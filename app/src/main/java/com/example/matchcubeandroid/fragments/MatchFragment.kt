package com.example.matchcubeandroid.fragments

import android.R.string
import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.matchcubeandroid.R
import com.example.matchcubeandroid.adapter.MatchTabTeamPlrAdapter
import com.example.matchcubeandroid.image.URLtoBitmapTask
import com.example.matchcubeandroid.model.LocateModel
import com.example.matchcubeandroid.model.PlayerDetail
import com.example.matchcubeandroid.model.PlayerDetailModel
import com.example.matchcubeandroid.retrofit.Client
import com.example.matchcubeandroid.sharedPreferences.MySharedPreferences
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_match.*
import okhttp3.internal.notify
import okhttp3.internal.notifyAll
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.URL

class MatchFragment : Fragment() {

    private lateinit var viewPagers: ViewPager
    private lateinit var tabLayouts: TabLayout

    private val matchLocateSido:ArrayList<String> = ArrayList()
    private val matchLocatecode:ArrayList<Int> = ArrayList() // 시 도의 코드값을 저장 해 놓을 ArrayList
    private val matchLocategungu:ArrayList<String> = ArrayList()
    private lateinit var playerDetailName: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_match, container, false)

        var context: Context = view.context
        //var cityCode: Int = 11 // 서울 cityCode
        var i:Int = 0 // 제어변수

        var locateArrayAdapter: ArrayAdapter<String> = ArrayAdapter(context, android.R.layout.simple_spinner_item, matchLocateSido)
        var locateGunguAdapter: ArrayAdapter<String> = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, matchLocategungu)
        locateArrayAdapter.setNotifyOnChange(true)
        locateGunguAdapter.setNotifyOnChange(true)
        locateArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        locateGunguAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // api interface : city -> 시 * 도만 출력
        Client.retrofitService.locate().enqueue(object : Callback<LocateModel>, AdapterView.OnItemSelectedListener {
            override fun onResponse(call: Call<LocateModel>, response: Response<LocateModel>) {
                if (response.body()?.statusCode == 100) { // 200 : successful

                    val data = response.body()?.data
                    val bodyData = response.body()!!
                    data?.let { Result.success(data) }
                    var sizeArr: Int = bodyData.data!!.size.toInt()
                    Toast.makeText(context, response.body()?.responseMessage, Toast.LENGTH_SHORT).show()
                    Log.d("locateTag", "${response.body()?.toString()}")

                    for (i in i..(sizeArr - 1)) {
                        matchLocateSido.add(bodyData.data!![i].name)
                        matchLocatecode.add(bodyData.data!![i].code)
                    }

                    // 시 도 스피너 어댑터 지정
                    matchLocateSpinner?.adapter = locateArrayAdapter
                    matchLocateSpinner.setSelection(3) // 스피너 default 값
                    matchLocateSpinner.onItemSelectedListener = this

                } else {
                    Toast.makeText(context, response.body()?.responseMessage, Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<LocateModel>, t: Throwable) {
                t?.message?.let {
                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                }
            }
            /** 시, 도 스피너가 선택되었을 때의 이벤트 처리 부분 **/
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                            Client.retrofitService.locateDetail(matchLocatecode[position]).enqueue(object : Callback<LocateModel> {
                                override fun onResponse(call: Call<LocateModel>, response: Response<LocateModel>) {
                                    if (response.body()?.statusCode == 100) { // 200 : successful
                                        val data = response.body()?.data
                                        val bodyData = response.body()?.data!!
                                        data?.let { Result.success(data) }
                                        matchLocategungu.clear() // 군,구 스피너 ArrayList 갱신
                                        val sizeArr: Int = bodyData.size
                                        Toast.makeText(context, response.body()?.responseMessage, Toast.LENGTH_SHORT).show()
                                        for (i in i..(sizeArr-1)) {
                                            matchLocategungu.add(response.body()!!.data[i].name) // 군 구 데이터 배열 저장
                                        }

                                        

                                    } else {
                                        Toast.makeText(context, response.body()?.responseMessage, Toast.LENGTH_SHORT).show()
                                    }
                                    matchGunguSpinner?.adapter = locateGunguAdapter // 어댑터 지정
                                }
                                override fun onFailure(call: Call<LocateModel>, t: Throwable) {
                                    t?.message?.let {
                                        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                                    }
                                }
                            })
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        })

        /***여기에 선수 상세정보 불러와서 userId에 따라 세부 종목 불러우는 코드가 들어가야 한다***/
        Client.retrofitService.playersDetail(1).enqueue(object : Callback<PlayerDetailModel>{
            override fun onResponse(call: Call<PlayerDetailModel>, response: Response<PlayerDetailModel>) {

                // 이미지 처리 객체
                var image_task: URLtoBitmapTask = URLtoBitmapTask()
                image_task = URLtoBitmapTask().apply {
                    url = URL(response.body()!!.data?.image)
                }
                var bitmap: Bitmap = image_task.execute().get()
                match_fragment_category_img.setImageBitmap(bitmap)
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