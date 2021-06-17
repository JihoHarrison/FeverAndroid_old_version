package com.example.matchcubeandroid.fragments

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.matchcubeandroid.R
import com.example.matchcubeandroid.adapter.MatchTabTeamPlrAdapter
import com.example.matchcubeandroid.model.LocateModel
import com.example.matchcubeandroid.model.PlayerDetailModel
import com.example.matchcubeandroid.retrofit.Client
import com.example.matchcubeandroid.sharedPreferences.MySharedPreferences
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_match.*
import kotlinx.android.synthetic.main.locate_dialog_gungu.*
import kotlinx.android.synthetic.main.locate_dialog_sido.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.StringBuilder

class MatchFragment : Fragment() {

    private lateinit var viewPagers: ViewPager
    private lateinit var tabLayouts: TabLayout
    private lateinit var indicator: View
    private lateinit var btnLocate: AppCompatButton
    private lateinit var sidoDialogRc: RecyclerView
    private lateinit var dialog: Dialog
    private lateinit var dialogGungu: Dialog

    private lateinit var btnBack: AppCompatImageButton
    private lateinit var btnGunguCancel: AppCompatImageButton

    private val matchLocateSido:ArrayList<String> = ArrayList()
    private val matchLocatecode:ArrayList<Int> = ArrayList() // 시 도의 코드값을 저장 해 놓을 ArrayList
    private val matchLocategungu:ArrayList<String> = ArrayList()

    private var indicatorWidth = 0
    var myLocation: String? = null /**이중 다이얼로그로 선택한 최종적인 나의 위치를 저장 해 놓을 문자열 변수.**/

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_match, container, false)
        val sidoDialogView = inflater.inflate(R.layout.locate_dialog_sido, null)

        btnLocate = view.findViewById(R.id.btnLocate)
        sidoDialogRc = sidoDialogView.findViewById(R.id.sidoDialogRc)
        tabLayouts = view.findViewById(R.id.matchTabLayout)
        viewPagers = view.findViewById(R.id.matchViewPager)
        indicator = view.findViewById(R.id.indicator)

        var context: Context = view.context

        //var cityCode: Int = 11 // 서울 cityCode
        var i:Int = 0 // 제어변수
        val sidoAdapter = LocateAdapter(context, matchLocateSido)
        val gunguAdapter = LocateGunguAdapter(context, matchLocategungu)



        // 위치 입력하기 위해 누르는 버튼(시 * 도)
        btnLocate.setOnClickListener {
            matchLocateSido.clear()
            Client.retrofitService.locate().enqueue(object : Callback<LocateModel>{
                override fun onResponse(call: Call<LocateModel>, response: Response<LocateModel>) {
                    if(response.body()!!.statusCode == 100){
                        var size = response.body()!!.data?.size

                        for(i in 0..size-1){
                            matchLocateSido.apply {
                                add(
                                    response.body()?.data?.get(i)!!.name
                                )
                            }
                            matchLocatecode.apply {
                                add(
                                    response.body()?.data?.get(i)!!.code
                                )
                            }
                        }
                        showSidoDialog(context)
                    }
                }

                override fun onFailure(call: Call<LocateModel>, t: Throwable) {
                    Toast.makeText(context,"시*도 조회 실패", Toast.LENGTH_SHORT).show()
                }

                fun showSidoDialog(context1: Context){
                    dialog = Dialog(context1)
                    dialogGungu = Dialog(context1)
                    dialog.setCancelable(false)
                    dialog.setContentView(R.layout.locate_dialog_sido)

                    var recyclerView: RecyclerView = dialog.findViewById(R.id.sidoDialogRc)
                    var btnCancel = dialog.findViewById<AppCompatImageButton>(R.id.btnCancel)
                    var btngungucancel = dialogGungu.findViewById<AppCompatImageButton>(R.id.btnGunguCancel)


                    recyclerView.adapter = sidoAdapter

                    sidoAdapter.setItemClickListener(object : LocateAdapter.OnItemClickListener{
                        /**군, 구 배열을 적용해야 함.**/
                        override fun onClick(v: View, position: Int) {
                            myLocation = null // 다이얼로그가 생성되기 전에 내 전역 위치는 항상 초기화되어 사용자가 입력하는 값을 다시 가져와야함
                            matchLocategungu.clear() // 이 부분 역시 리사이클러뷰에 누적되는 현상을 막기 위함
                            dialog.dismiss() // 클릭 시 this는 사라지고 다음 다이얼로그 상자가 나와야 함
                            /**리사이클러뷰 클릭 이벤트**/
                            Client.retrofitService.locateDetail(matchLocatecode[position]).enqueue(object: Callback<LocateModel>{
                                override fun onResponse(call: Call<LocateModel>, response: Response<LocateModel>) {
                                        var size = response.body()!!.data.size
                                        //Toast.makeText(context, response.message().toString(), Toast.LENGTH_SHORT).show()
                                        for(i in 0..size - 1){
                                            matchLocategungu.apply {
                                                add(
                                                    response.body()!!.data[i].name
                                                )
                                            }
                                        }
                                    dialog.dismiss()
                                    dialogGungu.setCancelable(true)
                                    /**군, 구 다이얼로그 창에 있는 뷰(back, cancel 버튼들)들을 처리하는 부분**/
                                    dialogGungu.setContentView(R.layout.locate_dialog_gungu).let{
                                        btnBack = dialogGungu.findViewById(R.id.btnBackGungu)
                                        btnGunguCancel = dialogGungu.findViewById(R.id.btnGunguCancel)
                                        btnBack.setOnClickListener {
                                            dialogGungu.dismiss()
                                            dialog.show()
                                        }
                                        btnGunguCancel.setOnClickListener {
                                            dialogGungu.dismiss()
                                        }
                                    }
                                    var recyclerViewGungu: RecyclerView = dialogGungu.findViewById(R.id.gunguDialogRc)
                                    recyclerViewGungu.adapter = gunguAdapter
                                    recyclerViewGungu.layoutManager = LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false)
                                    dialogGungu.show()

                                    gunguAdapter.setItemClickListener(object : LocateGunguAdapter.OnItemClickListener{
                                        override fun onClick(v: View, position: Int) {
                                            /** 위치를 선택하세요 버튼의 텍스트를 지정 해 줘야 함 **/
                                            btnLocate.text = matchLocategungu[position]
                                            dialogGungu.dismiss()
                                            myLocation += matchLocategungu // 전역 내 위치


                                        }



                                    })


                                }
                                override fun onFailure(call: Call<LocateModel>, t: Throwable) {

                                }

                            })

                        }
                    })

                    recyclerView.layoutManager = LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false)
                    btnCancel.setOnClickListener {
                        dialog.dismiss()
                    }
                    dialog.show()
                }

            })

        }

        /***여기에 선수 상세정보 불러와서 userId에 따라 세부 종목 불러우는 코드가 들어가야 한다***/
        Client.retrofitService.playersDetail(MySharedPreferences.getUserId(context).toLong()).enqueue(object : Callback<PlayerDetailModel>{
            override fun onResponse(call: Call<PlayerDetailModel>, response: Response<PlayerDetailModel>) {

                // 이미지 처리 객체
//                var image_task: URLtoBitmapTask = URLtoBitmapTask()
//                image_task = URLtoBitmapTask().apply {
//                    url = URL(response.body()!!.data?.image.toString())
//                }
//                var bitmap: Bitmap = image_task.execute().get()
//                match_fragment_category_img.setImageBitmap(bitmap)
            }

            override fun onFailure(call: Call<PlayerDetailModel>, t: Throwable) {
                Toast.makeText(context, "선수 정보 조회 실패", Toast.LENGTH_SHORT).show()
            }


        })
        if(myLocation == null){
            Toast.makeText(context, "mylocation null", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, myLocation, Toast.LENGTH_SHORT).show()
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setUpViewPager()

        viewPagers.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                val params = indicator.getLayoutParams() as FrameLayout.LayoutParams
                //Multiply positionOffset with indicatorWidth to get translation
                val translationOffset: Float = (positionOffset + position) * indicatorWidth
                params.leftMargin = translationOffset.toInt()
                indicator.setLayoutParams(params)
            }

            override fun onPageSelected(position: Int) {

            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })

        /**선수, 팀 탭이 선택 , 재선택 , 선택되지 않았을 시**/
        tabLayouts.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun setUpViewPager(){

        var adapter = MatchTabTeamPlrAdapter(fragmentManager!!)
        adapter.addFragment(Matchtabteam(), "팀")
        adapter.addFragment(Matchtabplayer(), "선수")

        viewPagers!!.adapter = adapter
        tabLayouts!!.setupWithViewPager(viewPagers)

        tabLayouts.post(Runnable{
            /**View(id:indicator)로 tablayout의 길이를 계산하여 tabItems를 둥글게 적용**/
            indicatorWidth = tabLayouts.width / tabLayouts.tabCount
            var indicatorParams: FrameLayout.LayoutParams = indicator.layoutParams as FrameLayout.LayoutParams
            indicatorParams.width = indicatorWidth
            indicator.layoutParams = indicatorParams

        })
    }
}
/**시 도 리사이클러뷰 어댑터 클래스. ViewHolder 생성 시점에 setOnClickListener 재정의 함. **/
class LocateAdapter(context: Context, private val dataset: ArrayList<String>) : RecyclerView.Adapter<LocateAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var textView: TextView = view.findViewById<TextView>(R.id.txtLocate)
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.recycler_items_white, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.textView.text = dataset[position]

        // 인터페이스로 각각의 아이템이 지금 이 클래스 외부에서 클릭 되었을 때 onClick 함수 재정의 시킴.
        viewHolder.itemView.setOnClickListener{
            itemClickListener.onClick(it, position)
        }
    }

    interface OnItemClickListener{
        fun onClick(v: View, position: Int)
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener){
        this.itemClickListener = onItemClickListener
    }

    private lateinit var itemClickListener : OnItemClickListener

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataset.size
}
/**군 구 리사이클러뷰 어댑터 클래스. LocateSidoAdapter와 마찬가지로 ViewHolder 생성 시점에 setOnClickListener 재정의 함. **/
class LocateGunguAdapter(context: Context, private val dataset: ArrayList<String>) : RecyclerView.Adapter<LocateGunguAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var textView: TextView = view.findViewById<TextView>(R.id.txtLocate)
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.recycler_items_white, viewGroup, false)

        return ViewHolder(view)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataset.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = dataset.get(position)
        holder.itemView.setOnClickListener{
            itemClickListener.onClick(it, position)
        }
    }

    interface OnItemClickListener{
        fun onClick(v: View, position: Int)
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener){
        this.itemClickListener = onItemClickListener
    }

    private lateinit var itemClickListener : OnItemClickListener
}