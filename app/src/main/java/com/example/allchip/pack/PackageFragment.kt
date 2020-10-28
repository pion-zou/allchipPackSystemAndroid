package com.example.allchip.pack

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.databinding.tool.util.StringUtils
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.allchip.R
import com.example.allchip.data.event.NavigateUpEvent
import com.example.allchip.data.model.Good
import com.example.allchip.ui.ScanActivity
import com.google.gson.Gson
import com.google.zxing.client.android.Intents
import org.greenrobot.eventbus.EventBus
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

class PackageFragment: Fragment() {
    private lateinit var btnScan:Button
    private lateinit var layoutGoods:RecyclerView
    private lateinit var tvNumber:TextView
    private lateinit var btnUpload:Button
    private var adapter: ScanGoodAdapter? = null
    private var listGoods = arrayListOf<Good>()
    private var contractNumber = ""
    private var viewModel:PackViewModel?=null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this, PackViewModelFactory()).get(PackViewModel::class.java)
        val rootView = inflater.inflate(R.layout.fragment_package, container, false)
        btnScan = rootView.findViewById(R.id.btn_scan)
        btnUpload = rootView.findViewById(R.id.btn_upload)
        layoutGoods = rootView.findViewById(R.id.list_good)
        tvNumber = rootView.findViewById(R.id.number)
        tvNumber.text = "合同号：$contractNumber"
        btnScan.setOnClickListener {
            startActivityForResult(Intent(activity, ScanActivity::class.java), 0)
        }
        btnUpload.setOnClickListener {
            viewModel?.uploadPackage(listGoods)
        }

        viewModel?.uploadPackageLiveDate?.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if (it.status == 1) {
                Toast.makeText(activity, it.msg, Toast.LENGTH_SHORT).show()
                EventBus.getDefault().post(NavigateUpEvent(true))
                listGoods.clear()
                adapter?.notifyDataSetChanged()
            } else {
                Toast.makeText(activity, it.msg, Toast.LENGTH_SHORT).show()
            }
        })

        return rootView
    }

    @SuppressLint("ShowToast", "SimpleDateFormat")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            0 -> {
                if (!StringUtils.isNotBlank(contractNumber)) {
                    Toast.makeText(activity, "合同号为空", Toast.LENGTH_SHORT)
                    return
                }
                if (resultCode == Activity.RESULT_OK) {
                    var text = data?.getStringExtra(Intents.Scan.RESULT)
                    var good: Good? = null
                    try {
                        good = Gson().fromJson(text, Good::class.java)
                    } catch (e: Exception) {
                        Toast.makeText(
                            activity,
                            "解析失败，无法识别该二维码，错误信息:${e.message}",
                            Toast.LENGTH_LONG
                        )
                        return
                    }
                    if (adapter == null) {
                        adapter = ScanGoodAdapter(listGoods)
                        layoutGoods.layoutManager = LinearLayoutManager(activity)
                        adapter?.setOnItemClickListener { view, i ->
                            when (view.id) {
                                R.id.btn_delete -> {
                                    listGoods.removeAt(i)
                                    if (listGoods.isEmpty()) {
                                        EventBus.getDefault().post(NavigateUpEvent(true))
                                        btnUpload.visibility = View.GONE
                                    }else{
                                        btnUpload.visibility = View.VISIBLE

                                    }
                                    adapter?.notifyItemRemoved(i)
                                }
                            }
                        }
                    }
                    if (contractNumber != good.number) {
                        Toast.makeText(activity, "扫描货物合同号和选择合同号不一致", Toast.LENGTH_LONG)
                        return
                    }
                    //去重复
                    for (g in listGoods) {
                        if (g.item_index == good.item_index) {
                            Toast.makeText(activity, "该型号已经装包", Toast.LENGTH_LONG)
                            return
                        }
                    }
                    //装包时间
                    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss" , Locale.getDefault())
                    val date: Date = Date(System.currentTimeMillis())
                    good.package_time = format.format(date)
                    listGoods.add(good)
                    btnUpload.visibility = View.VISIBLE
                    EventBus.getDefault().post(NavigateUpEvent(false))
                    layoutGoods.adapter = adapter
                    adapter?.notifyDataSetChanged()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        contractNumber = arguments?.getString("number")?:""
    }

}