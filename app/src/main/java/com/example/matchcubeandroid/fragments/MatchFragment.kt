package com.example.matchcubeandroid.fragments

import android.R.string
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Insets.add
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.view.OneShotPreDrawListener.add
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.matchcubeandroid.R
import com.example.matchcubeandroid.adapter.MatchTabTeamPlrAdapter
import com.example.matchcubeandroid.adapter.MatchtabTeamsAdapter
import com.example.matchcubeandroid.model.LocateModel
import com.example.matchcubeandroid.model.PlayerDetail
import com.example.matchcubeandroid.model.PlayerDetailModel
import com.example.matchcubeandroid.retrofit.Client
import com.example.matchcubeandroid.sharedPreferences.MySharedPreferences
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_match.*
import kotlinx.android.synthetic.main.fragment_matchtabteam.*
import kotlinx.android.synthetic.main.locate_dialog_gungu.*
import kotlinx.android.synthetic.main.locate_dialog_sido.*
import okhttp3.internal.notify
import okhttp3.internal.notifyAll
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.URL

class MatchFragment : Fragment() {

    private lateinit var viewPagers: ViewPager
    private lateinit var tabLayouts: TabLayout
    private lateinit var btnLocate: AppCompatButton
    private lateinit var sidoDialogRc: RecyclerView

    private lateinit var btnBack: AppCompatImageButton

    private val matchLocateSido:ArrayList<String> = ArrayList()
    private val matchLocatecode:ArrayList<Int> = ArrayList() // 시 도의 코드값을 저장 해 놓을 ArrayList
    private val matchLocategungu:ArrayList<String> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_match, container, false)
        val sidoDialogView = inflater.inflate(R.layout.locate_dialog_sido, null)

        btnLocate = view.findViewById(R.id.btnLocate)
        sidoDialogRc = sidoDialogView.findViewById(R.id.sidoDialogRc)
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
                    val dialog = Dialog(context)
                    val dialogGungu = Dialog(context)
                    dialog.setCancelable(false)
                    dialog.setContentView(R.layout.locate_dialog_sido)

                    var recyclerView: RecyclerView = dialog.findViewById(R.id.sidoDialogRc)
                    var btnCancel = dialog.findViewById<AppCompatImageButton>(R.id.btnCancel)

                    recyclerView.adapter = sidoAdapter

                    sidoAdapter.setItemClickListener(object : LocateAdapter.OnItemClickListener{
                        /**군, 구 배열을 적용해야 함.**/
                        override fun onClick(v: View, position: Int) {
                            matchLocategungu.clear()
                            dialog.dismiss()
                            /**리사이클러뷰 클릭 이벤트**/
                            Client.retrofitService.locateDetail(matchLocatecode[position]).enqueue(object: Callback<LocateModel>{
                                override fun onResponse(call: Call<LocateModel>, response: Response<LocateModel>) {
                                        var size = response.body()!!.data.size
                                        Toast.makeText(context, response.message().toString(), Toast.LENGTH_SHORT).show()
                                        for(i in 0..size - 1){
                                            matchLocategungu.apply {
                                                add(
                                                    response.body()!!.data.get(i).name
                                                )
                                            }
                                        }
                                    dialog.dismiss()
                                    dialogGungu.setCancelable(true)
                                    dialogGungu.setContentView(R.layout.locate_dialog_gungu).let{
                                        btnBack = dialogGungu.findViewById(R.id.btnBackGungu)
                                        btnBack.setOnClickListener {
                                            dialogGungu.dismiss()
                                            dialog.show()
                                        }
                                    }
                                    var recyclerViewGungu: RecyclerView = dialogGungu.findViewById(R.id.gunguDialogRc)
                                    recyclerViewGungu.adapter = gunguAdapter
                                    recyclerViewGungu.layoutManager = LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false)
                                    dialogGungu.show()
                                    gunguAdapter.setItemClickListener(object : LocateGunguAdapter.OnItemClickListener{
                                        override fun onClick(v: View, position: Int) {
                                            /** 위치를 선택하세요 버튼의 텍스트를 지정 해 줘야 함 **/
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

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setUpViewPager()


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
        viewPagers = matchViewPager
        tabLayouts = matchTabLayout

        var adapter = MatchTabTeamPlrAdapter(fragmentManager!!)
        adapter.addFragment(Matchtabteam(), "팀")
        adapter.addFragment(Matchtabplayer(), "선수")

        viewPagers!!.adapter = adapter
        tabLayouts!!.setupWithViewPager(viewPagers)
    }
}
// 시 도 위치를 리사이클러뷰에 연결시켜주는 어뎁터
class LocateAdapter(context: Context, private val dataset: ArrayList<String>) : RecyclerView.Adapter<LocateAdapter.ViewHolder>() {
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var textView = view.findViewById<TextView>(R.id.txtLocate)
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
        viewHolder.textView.text = dataset.get(position)

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

class LocateGunguAdapter(context: Context, private val dataset: ArrayList<String>) : RecyclerView.Adapter<LocateGunguAdapter.ViewHolder>() {
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var textView = view.findViewById<TextView>(R.id.txtLocate)
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
    }

    interface OnItemClickListener{
        fun onClick(v: View, position: Int)
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener){
        this.itemClickListener = onItemClickListener
    }

    private lateinit var itemClickListener : OnItemClickListener
}