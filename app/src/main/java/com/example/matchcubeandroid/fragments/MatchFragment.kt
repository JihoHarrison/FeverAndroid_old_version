package com.example.matchcubeandroid.fragments

import android.R.string
import android.content.Context
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

class MatchFragment : Fragment() {

    private lateinit var viewPagers: ViewPager
    private lateinit var tabLayouts: TabLayout

    private val matchLocateSido:ArrayList<String> = ArrayList()
    private val matchLocategungu:ArrayList<String> = ArrayList()
    private lateinit var playerDetailName: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_match, container, false)

        var context: Context = view.context
        //var cityCode: Int = 11 // 서울 cityCode
        var i:Int = 0 // 제어변수

        var locateArrayAdapter: ArrayAdapter<String> = ArrayAdapter(context, android.R.layout.simple_spinner_item, matchLocateSido)
        var locateGunguAdapter: ArrayAdapter<String> = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, matchLocategungu)
        locateArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        locateGunguAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        var arrayAdapter = ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, matchLocateSido)
//        matchLocateSpinner.setOnItemClickListener(AdapterView.OnItemSelectedListener() {

//        })

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
                    // response.body()?.data!![0].code
                    var a = response.body()?.data!![0].code
                    Log.d("sido", "${a}")
                    var b = response.body()?.data!![0].name
                    Log.d("sido", "${sizeArr}")

                    for (i in i..(sizeArr - 1)) {
                        Log.d("sido", "${bodyData.data!![i].code}")
                        Log.d("sido", "${bodyData.data!![i].name}")
                        matchLocateSido.add(bodyData.data!![i].name)
                        //matchLocateSido!![i] = bodyData.data!![i].name 틀린 방법.
                        Log.d("helloworld", matchLocateSido[i])
                    }
                    // 시 도 스피너 어댑터 지정
                    matchLocateSpinner?.adapter = locateArrayAdapter
                    matchLocateSpinner.setSelection(3)
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
                if (parent != null) {
                    when(position){
                        0 -> // 강원도
                            // PATH variable -> {cityCode}
                            // api interface : city/{cityCode}/si-gun-gu -> 시*도 별로 구*군 출력
                            Client.retrofitService.locateDetail(32).enqueue(object : Callback<LocateModel> {
                                override fun onResponse(call: Call<LocateModel>, response: Response<LocateModel>) {
                                    if (response.body()?.statusCode == 100) { // 200 : successful
                                        val data = response.body()?.data
                                        val bodyData = response.body()?.data!!
                                        data?.let { Result.success(data) }

                                        val sizeArr: Int = bodyData.size
                                        Toast.makeText(context, response.body()?.responseMessage, Toast.LENGTH_SHORT).show()
                                        for (i in i..(sizeArr-1)) {
                                            matchLocategungu.add(response.body()!!.data[i].toString()) // 군 구 데이터 배열 저장 완료
                                        }
                                    } else {
                                        Toast.makeText(context, response.body()?.responseMessage, Toast.LENGTH_SHORT).show()
                                    }

                                    matchGunguSpinner?.adapter = locateGunguAdapter
                                }
                                override fun onFailure(call: Call<LocateModel>, t: Throwable) {
                                    t?.message?.let {
                                        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                                    }
                                }
                            })
                        1 -> // 경기도
                            // PATH variable -> {cityCode}
                            // api interface : city/{cityCode}/si-gun-gu -> 시*도 별로 구*군 출력
                            Client.retrofitService.locateDetail(31).enqueue(object : Callback<LocateModel> {
                                override fun onResponse(call: Call<LocateModel>, response: Response<LocateModel>) {
                                    if (response.body()?.statusCode == 100) { // 200 : successful
                                        val data = response.body()?.data
                                        val bodyData = response.body()?.data!!
                                        data?.let { Result.success(data) }

                                        val sizeArr: Int = bodyData.size
                                        Toast.makeText(context, response.body()?.responseMessage, Toast.LENGTH_SHORT).show()
                                        for (i in i..(sizeArr-1)) {
                                            matchLocategungu.add(response.body()!!.data[i].toString()) // 군 구 데이터 배열 저장 완료
                                        }
                                    } else {
                                        Toast.makeText(context, response.body()?.responseMessage, Toast.LENGTH_SHORT).show()
                                    }
                                    matchGunguSpinner?.adapter = locateGunguAdapter
                                }
                                override fun onFailure(call: Call<LocateModel>, t: Throwable) {
                                    t?.message?.let {
                                        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                                    }
                                }
                            })
                        2 -> // 경상남도
                            // PATH variable -> {cityCode}
                            // api interface : city/{cityCode}/si-gun-gu -> 시*도 별로 구*군 출력
                            Client.retrofitService.locateDetail(38).enqueue(object : Callback<LocateModel> {
                                override fun onResponse(call: Call<LocateModel>, response: Response<LocateModel>) {
                                    if (response.body()?.statusCode == 100) { // 200 : successful
                                        val data = response.body()?.data
                                        val bodyData = response.body()?.data!!
                                        data?.let { Result.success(data) }

                                        val sizeArr: Int = bodyData.size
                                        Toast.makeText(context, response.body()?.responseMessage, Toast.LENGTH_SHORT).show()
                                        for (i in i..(sizeArr-1)) {
                                            matchLocategungu.add(response.body()!!.data[i].toString()) // 군 구 데이터 배열 저장 완료
                                        }
                                    } else {
                                        Toast.makeText(context, response.body()?.responseMessage, Toast.LENGTH_SHORT).show()
                                    }
                                    matchGunguSpinner?.adapter = locateGunguAdapter
                                }
                                override fun onFailure(call: Call<LocateModel>, t: Throwable) {
                                    t?.message?.let {
                                        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                                    }
                                }
                            })
                        3 -> // 경상북도
                            // PATH variable -> {cityCode}
                            // api interface : city/{cityCode}/si-gun-gu -> 시*도 별로 구*군 출력
                            Client.retrofitService.locateDetail(37).enqueue(object : Callback<LocateModel> {
                                override fun onResponse(call: Call<LocateModel>, response: Response<LocateModel>) {
                                    if (response.body()?.statusCode == 100) { // 200 : successful
                                        val data = response.body()?.data
                                        val bodyData = response.body()?.data!!
                                        data?.let { Result.success(data) }

                                        val sizeArr: Int = bodyData.size
                                        Toast.makeText(context, response.body()?.responseMessage, Toast.LENGTH_SHORT).show()
                                        for (i in i..(sizeArr-1)) {
                                            matchLocategungu.add(response.body()!!.data[i].toString()) // 군 구 데이터 배열 저장 완료
                                        }
                                    } else {
                                        Toast.makeText(context, response.body()?.responseMessage, Toast.LENGTH_SHORT).show()
                                    }
                                    matchGunguSpinner?.adapter = locateGunguAdapter
                                }
                                override fun onFailure(call: Call<LocateModel>, t: Throwable) {
                                    t?.message?.let {
                                        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                                    }
                                }
                            })
                        4 -> // 광주광역시
                            // PATH variable -> {cityCode}
                            // api interface : city/{cityCode}/si-gun-gu -> 시*도 별로 구*군 출력
                            Client.retrofitService.locateDetail(24).enqueue(object : Callback<LocateModel> {
                                override fun onResponse(call: Call<LocateModel>, response: Response<LocateModel>) {
                                    if (response.body()?.statusCode == 100) { // 200 : successful
                                        val data = response.body()?.data
                                        val bodyData = response.body()?.data!!
                                        data?.let { Result.success(data) }

                                        val sizeArr: Int = bodyData.size
                                        Toast.makeText(context, response.body()?.responseMessage, Toast.LENGTH_SHORT).show()
                                        for (i in i..(sizeArr-1)) {
                                            matchLocategungu.add(response.body()!!.data[i].toString()) // 군 구 데이터 배열 저장 완료
                                        }
                                    } else {
                                        Toast.makeText(context, response.body()?.responseMessage, Toast.LENGTH_SHORT).show()
                                    }
                                    matchGunguSpinner?.adapter = locateGunguAdapter
                                }
                                override fun onFailure(call: Call<LocateModel>, t: Throwable) {
                                    t?.message?.let {
                                        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                                    }
                                }
                            })
                        5 -> // 대구광역시
                            // PATH variable -> {cityCode}
                            // api interface : city/{cityCode}/si-gun-gu -> 시*도 별로 구*군 출력
                            Client.retrofitService.locateDetail(23).enqueue(object : Callback<LocateModel> {
                                override fun onResponse(call: Call<LocateModel>, response: Response<LocateModel>) {
                                    if (response.body()?.statusCode == 100) { // 200 : successful
                                        val data = response.body()?.data
                                        val bodyData = response.body()?.data!!
                                        data?.let { Result.success(data) }

                                        val sizeArr: Int = bodyData.size
                                        Toast.makeText(context, response.body()?.responseMessage, Toast.LENGTH_SHORT).show()
                                        for (i in i..(sizeArr-1)) {
                                            matchLocategungu.add(response.body()!!.data[i].toString()) // 군 구 데이터 배열 저장 완료
                                        }
                                    } else {
                                        Toast.makeText(context, response.body()?.responseMessage, Toast.LENGTH_SHORT).show()
                                    }
                                    matchGunguSpinner?.adapter = locateGunguAdapter
                                }
                                override fun onFailure(call: Call<LocateModel>, t: Throwable) {
                                    t?.message?.let {
                                        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                                    }
                                }
                            })
                        6 -> // 대전광역시
                            // PATH variable -> {cityCode}
                            // api interface : city/{cityCode}/si-gun-gu -> 시*도 별로 구*군 출력
                            Client.retrofitService.locateDetail(25).enqueue(object : Callback<LocateModel> {
                                override fun onResponse(call: Call<LocateModel>, response: Response<LocateModel>) {
                                    if (response.body()?.statusCode == 100) { // 200 : successful
                                        val data = response.body()?.data
                                        val bodyData = response.body()?.data!!
                                        data?.let { Result.success(data) }

                                        val sizeArr: Int = bodyData.size
                                        Toast.makeText(context, response.body()?.responseMessage, Toast.LENGTH_SHORT).show()
                                        for (i in i..(sizeArr-1)) {
                                            matchLocategungu.add(response.body()!!.data[i].toString()) // 군 구 데이터 배열 저장 완료
                                        }
                                    } else {
                                        Toast.makeText(context, response.body()?.responseMessage, Toast.LENGTH_SHORT).show()
                                    }
                                    matchGunguSpinner?.adapter = locateGunguAdapter
                                }
                                override fun onFailure(call: Call<LocateModel>, t: Throwable) {
                                    t?.message?.let {
                                        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                                    }
                                }
                            })
                        7 -> // 부산광역시
                            // PATH variable -> {cityCode}
                            // api interface : city/{cityCode}/si-gun-gu -> 시*도 별로 구*군 출력
                            Client.retrofitService.locateDetail(21).enqueue(object : Callback<LocateModel> {
                                override fun onResponse(call: Call<LocateModel>, response: Response<LocateModel>) {
                                    if (response.body()?.statusCode == 100) { // 200 : successful
                                        val data = response.body()?.data
                                        val bodyData = response.body()?.data!!
                                        data?.let { Result.success(data) }

                                        val sizeArr: Int = bodyData.size
                                        Toast.makeText(context, response.body()?.responseMessage, Toast.LENGTH_SHORT).show()
                                        for (i in i..(sizeArr-1)) {
                                            matchLocategungu.add(response.body()!!.data[i].toString()) // 군 구 데이터 배열 저장 완료
                                        }
                                    } else {
                                        Toast.makeText(context, response.body()?.responseMessage, Toast.LENGTH_SHORT).show()
                                    }
                                    matchGunguSpinner?.adapter = locateGunguAdapter
                                }
                                override fun onFailure(call: Call<LocateModel>, t: Throwable) {
                                    t?.message?.let {
                                        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                                    }
                                }
                            })
                        8 -> // 서울특별시
                            // PATH variable -> {cityCode}
                            // api interface : city/{cityCode}/si-gun-gu -> 시*도 별로 구*군 출력
                            Client.retrofitService.locateDetail(11).enqueue(object : Callback<LocateModel> {
                                override fun onResponse(call: Call<LocateModel>, response: Response<LocateModel>) {
                                    if (response.body()?.statusCode == 100) { // 200 : successful
                                        val data = response.body()?.data
                                        val bodyData = response.body()?.data!!
                                        data?.let { Result.success(data) }

                                        val sizeArr: Int = bodyData.size
                                        Toast.makeText(context, response.body()?.responseMessage, Toast.LENGTH_SHORT).show()
                                        for (i in i..(sizeArr-1)) {
                                            matchLocategungu.add(response.body()!!.data[i].toString()) // 군 구 데이터 배열 저장 완료
                                        }
                                    } else {
                                        Toast.makeText(context, response.body()?.responseMessage, Toast.LENGTH_SHORT).show()
                                    }
                                    matchGunguSpinner?.adapter = locateGunguAdapter
                                }
                                override fun onFailure(call: Call<LocateModel>, t: Throwable) {
                                    t?.message?.let {
                                        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                                    }
                                }
                            })
                        9 -> // 세종시
                            // PATH variable -> {cityCode}
                            // api interface : city/{cityCode}/si-gun-gu -> 시*도 별로 구*군 출력
                            Client.retrofitService.locateDetail(41).enqueue(object : Callback<LocateModel> {
                                override fun onResponse(call: Call<LocateModel>, response: Response<LocateModel>) {
                                    if (response.body()?.statusCode == 100) { // 200 : successful
                                        val data = response.body()?.data
                                        val bodyData = response.body()?.data!!
                                        data?.let { Result.success(data) }

                                        val sizeArr: Int = bodyData.size
                                        Toast.makeText(context, response.body()?.responseMessage, Toast.LENGTH_SHORT).show()
                                        for (i in i..(sizeArr-1)) {
                                            matchLocategungu.add(response.body()!!.data[i].toString()) // 군 구 데이터 배열 저장 완료
                                        }
                                    } else {
                                        Toast.makeText(context, response.body()?.responseMessage, Toast.LENGTH_SHORT).show()
                                    }
                                    matchGunguSpinner?.adapter = locateGunguAdapter
                                }
                                override fun onFailure(call: Call<LocateModel>, t: Throwable) {
                                    t?.message?.let {
                                        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                                    }
                                }
                            })
                        10 -> // 울산광역시
                            // PATH variable -> {cityCode}
                            // api interface : city/{cityCode}/si-gun-gu -> 시*도 별로 구*군 출력
                            Client.retrofitService.locateDetail(26).enqueue(object : Callback<LocateModel> {
                                override fun onResponse(call: Call<LocateModel>, response: Response<LocateModel>) {
                                    if (response.body()?.statusCode == 100) { // 200 : successful
                                        val data = response.body()?.data
                                        val bodyData = response.body()?.data!!
                                        data?.let { Result.success(data) }

                                        val sizeArr: Int = bodyData.size
                                        Toast.makeText(context, response.body()?.responseMessage, Toast.LENGTH_SHORT).show()
                                        for (i in i..(sizeArr-1)) {
                                            matchLocategungu.add(response.body()!!.data[i].toString()) // 군 구 데이터 배열 저장 완료
                                        }
                                    } else {
                                        Toast.makeText(context, response.body()?.responseMessage, Toast.LENGTH_SHORT).show()
                                    }
                                    matchGunguSpinner?.adapter = locateGunguAdapter
                                }
                                override fun onFailure(call: Call<LocateModel>, t: Throwable) {
                                    t?.message?.let {
                                        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                                    }
                                }
                            })
                        11 -> // 인천광역시
                            // PATH variable -> {cityCode}
                            // api interface : city/{cityCode}/si-gun-gu -> 시*도 별로 구*군 출력
                            Client.retrofitService.locateDetail(22).enqueue(object : Callback<LocateModel> {
                                override fun onResponse(call: Call<LocateModel>, response: Response<LocateModel>) {
                                    if (response.body()?.statusCode == 100) { // 200 : successful
                                        val data = response.body()?.data
                                        val bodyData = response.body()?.data!!
                                        data?.let { Result.success(data) }

                                        val sizeArr: Int = bodyData.size
                                        Toast.makeText(context, response.body()?.responseMessage, Toast.LENGTH_SHORT).show()
                                        for (i in i..(sizeArr-1)) {
                                            matchLocategungu.add(response.body()!!.data[i].toString()) // 군 구 데이터 배열 저장 완료
                                        }
                                    } else {
                                        Toast.makeText(context, response.body()?.responseMessage, Toast.LENGTH_SHORT).show()
                                    }
                                    matchGunguSpinner?.adapter = locateGunguAdapter
                                }
                                override fun onFailure(call: Call<LocateModel>, t: Throwable) {
                                    t?.message?.let {
                                        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                                    }
                                }
                            })
                        12 -> // 전라남도
                            // PATH variable -> {cityCode}
                            // api interface : city/{cityCode}/si-gun-gu -> 시*도 별로 구*군 출력
                            Client.retrofitService.locateDetail(36).enqueue(object : Callback<LocateModel> {
                                override fun onResponse(call: Call<LocateModel>, response: Response<LocateModel>) {
                                    if (response.body()?.statusCode == 100) { // 200 : successful
                                        val data = response.body()?.data
                                        val bodyData = response.body()?.data!!
                                        data?.let { Result.success(data) }

                                        val sizeArr: Int = bodyData.size
                                        Toast.makeText(context, response.body()?.responseMessage, Toast.LENGTH_SHORT).show()
                                        for (i in i..(sizeArr-1)) {
                                            matchLocategungu.add(response.body()!!.data[i].toString()) // 군 구 데이터 배열 저장 완료
                                        }
                                    } else {
                                        Toast.makeText(context, response.body()?.responseMessage, Toast.LENGTH_SHORT).show()
                                    }
                                    matchGunguSpinner?.adapter = locateGunguAdapter
                                }
                                override fun onFailure(call: Call<LocateModel>, t: Throwable) {
                                    t?.message?.let {
                                        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                                    }
                                }
                            })
                        13 -> // 전라북도
                            // PATH variable -> {cityCode}
                            // api interface : city/{cityCode}/si-gun-gu -> 시*도 별로 구*군 출력
                            Client.retrofitService.locateDetail(35).enqueue(object : Callback<LocateModel> {
                                override fun onResponse(call: Call<LocateModel>, response: Response<LocateModel>) {
                                    if (response.body()?.statusCode == 100) { // 200 : successful
                                        val data = response.body()?.data
                                        val bodyData = response.body()?.data!!
                                        data?.let { Result.success(data) }

                                        val sizeArr: Int = bodyData.size
                                        Toast.makeText(context, response.body()?.responseMessage, Toast.LENGTH_SHORT).show()
                                        for (i in i..(sizeArr-1)) {
                                            matchLocategungu.add(response.body()!!.data[i].toString()) // 군 구 데이터 배열 저장 완료
                                        }
                                    } else {
                                        Toast.makeText(context, response.body()?.responseMessage, Toast.LENGTH_SHORT).show()
                                    }
                                    matchGunguSpinner?.adapter = locateGunguAdapter
                                }
                                override fun onFailure(call: Call<LocateModel>, t: Throwable) {
                                    t?.message?.let {
                                        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                                    }
                                }
                            })
                        14 -> // 제주도
                            // PATH variable -> {cityCode}
                            // api interface : city/{cityCode}/si-gun-gu -> 시*도 별로 구*군 출력
                            Client.retrofitService.locateDetail(39).enqueue(object : Callback<LocateModel> {
                                override fun onResponse(call: Call<LocateModel>, response: Response<LocateModel>) {
                                    if (response.body()?.statusCode == 100) { // 200 : successful
                                        val data = response.body()?.data
                                        val bodyData = response.body()?.data!!
                                        data?.let { Result.success(data) }

                                        val sizeArr: Int = bodyData.size
                                        Toast.makeText(context, response.body()?.responseMessage, Toast.LENGTH_SHORT).show()
                                        for (i in i..(sizeArr-1)) {
                                            matchLocategungu.add(response.body()!!.data[i].toString()) // 군 구 데이터 배열 저장 완료
                                        }
                                    } else {
                                        Toast.makeText(context, response.body()?.responseMessage, Toast.LENGTH_SHORT).show()
                                    }
                                    matchGunguSpinner?.adapter = locateGunguAdapter
                                }
                                override fun onFailure(call: Call<LocateModel>, t: Throwable) {
                                    t?.message?.let {
                                        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                                    }
                                }
                            })
                        15 -> // 경기도
                            // PATH variable -> {cityCode}
                            // api interface : city/{cityCode}/si-gun-gu -> 시*도 별로 구*군 출력
                            Client.retrofitService.locateDetail(34).enqueue(object : Callback<LocateModel> {
                                override fun onResponse(call: Call<LocateModel>, response: Response<LocateModel>) {
                                    if (response.body()?.statusCode == 100) { // 200 : successful
                                        val data = response.body()?.data
                                        val bodyData = response.body()?.data!!
                                        data?.let { Result.success(data) }

                                        val sizeArr: Int = bodyData.size
                                        Toast.makeText(context, response.body()?.responseMessage, Toast.LENGTH_SHORT).show()
                                        for (i in i..(sizeArr-1)) {
                                            matchLocategungu.add(response.body()!!.data[i].toString()) // 군 구 데이터 배열 저장 완료
                                        }
                                    } else {
                                        Toast.makeText(context, response.body()?.responseMessage, Toast.LENGTH_SHORT).show()
                                    }
                                    matchGunguSpinner?.adapter = locateGunguAdapter
                                }
                                override fun onFailure(call: Call<LocateModel>, t: Throwable) {
                                    t?.message?.let {
                                        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                                    }
                                }
                            })
                        16 -> // 충청북도
                            // PATH variable -> {cityCode}
                            // api interface : city/{cityCode}/si-gun-gu -> 시*도 별로 구*군 출력

                            Client.retrofitService.locateDetail(33).enqueue(object : Callback<LocateModel> {
                                override fun onResponse(call: Call<LocateModel>, response: Response<LocateModel>) {
                                    if (response.body()?.statusCode == 100) { // 200 : successful
                                        val data = response.body()?.data
                                        val bodyData = response.body()?.data!!
                                        data?.let { Result.success(data) }

                                        val sizeArr: Int = bodyData.size
                                        Toast.makeText(context, response.body()?.responseMessage, Toast.LENGTH_SHORT).show()
                                        for (i in i..(sizeArr-1)) {
                                            matchLocategungu.add(response.body()!!.data[i].toString()) // 군 구 데이터 배열 저장 완료
                                        }
                                    } else {
                                        Toast.makeText(context, response.body()?.responseMessage, Toast.LENGTH_SHORT).show()
                                    }
                                    matchGunguSpinner?.adapter = locateGunguAdapter
                                }
                                override fun onFailure(call: Call<LocateModel>, t: Throwable) {
                                    t?.message?.let {
                                        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                                    }
                                }
                            })
                    }
                } else{
                    Toast.makeText(context, "위치가 선택되지 않았습니다.", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        })

        /***여기에 선수 상세정보 불러와서 userId에 따라 세부 종목 불러우는 코드가 들어가야 한다***/
        Client.retrofitService.playersDetail(1).enqueue(object : Callback<PlayerDetailModel>{
            override fun onResponse(call: Call<PlayerDetailModel>, response: Response<PlayerDetailModel>) {

                var category: String = response.body()!!.data?.categoryName
                Log.d("detailLog", category)
                categoryTxt.setText(category)
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