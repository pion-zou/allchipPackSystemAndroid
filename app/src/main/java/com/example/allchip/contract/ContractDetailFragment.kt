package com.example.allchip.contract

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.allchip.R
import com.example.allchip.data.LoadStatus
import com.example.allchip.data.model.Good

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class ContractDetailFragment : Fragment() {
    private lateinit var tvName: TextView
    private lateinit var tvCreator: TextView
    private lateinit var tvTime: TextView
    private lateinit var tvState: TextView
    private lateinit var tvLastUpdate: TextView
    private lateinit var tvEditor: TextView
    private lateinit var tvPublicTime: TextView
    private lateinit var tvMark: TextView
    private lateinit var viewModel: GoodViewModel
    private lateinit var btnRefresh: Button
    private lateinit var layoutRefresh: SwipeRefreshLayout
    private lateinit var layoutGood: RecyclerView
    private lateinit var btnExpand:TextView
    private lateinit var btnPackage:TextView
    private lateinit var layoutContract :LinearLayout

    private var contractId: Int = 0
    private var adapter: GoodAdapter? = null
    private var listGood = arrayListOf<Good>()

    private var expandFlag = true
    private var contractNumber = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel =
            ViewModelProviders.of(this, GoodViewModelFactory()).get(GoodViewModel::class.java)
        val rootview = inflater.inflate(R.layout.fragment_contract_detail, container, false)
        tvName = rootview.findViewById(R.id.name)
        tvCreator = rootview.findViewById(R.id.creator)
        tvTime = rootview.findViewById(R.id.time)
        tvState = rootview.findViewById(R.id.state)
        tvLastUpdate = rootview.findViewById(R.id.last_update)
        tvEditor = rootview.findViewById(R.id.editor)
        tvPublicTime = rootview.findViewById(R.id.public_time)
        tvMark = rootview.findViewById(R.id.mark)
        btnRefresh = rootview.findViewById(R.id.btn_refresh)
        layoutGood = rootview.findViewById(R.id.list_good)
        layoutRefresh = rootview.findViewById(R.id.layout_refresh)
        btnExpand = rootview.findViewById(R.id.btn_expand)
        layoutContract = rootview.findViewById(R.id.contract)
        btnPackage = rootview.findViewById(R.id.btn_package)
        btnRefresh.setOnClickListener {
            viewModel.getContractDetail(LoadStatus.REFRESH, contractId)
        }
        layoutRefresh.setOnRefreshListener {
            viewModel.getContractDetail(LoadStatus.INIT, contractId)
        }
        btnExpand.setOnClickListener {
            expandFlag = !expandFlag
            expandAnimation(expandFlag)
        }
        btnPackage.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("number", contractNumber)
            findNavController().navigate(
                R.id.action_SecondFragment_to_PackageFragment,
                bundle
            )
        }

        viewModel.contractDetail.observe(viewLifecycleOwner, Observer {
            if (it.isSucces()) {
                var contract = it.data?.data
                contract?.apply {
                    tvName.text = "合同号:${this.number}"
                    tvCreator.text = "创建者:${this.creator}"
                    tvTime.text = "创建时间:${this.create_time}"
                    tvState.text = "状态:${this.state}"
                    tvLastUpdate.text = "最近更新:${this.update_time}"
                    tvEditor.text = "${this.editor}"
                    tvPublicTime.text = "计划发货时间:${this.publish_time}"
                    tvMark.text = "备注:${this.remark}"

                    contractNumber = number
                }

                if (adapter == null) {
                    adapter = GoodAdapter(listGood)
                    adapter?.setOnItemClickListener { view: View, i: Int ->
                    }
                }
                layoutGood.adapter = adapter
                layoutGood.layoutManager = LinearLayoutManager(activity)
                when (it.data?.status) {
                    LoadStatus.INIT, LoadStatus.REFRESH -> {
                        listGood.clear()
                        listGood.addAll(contract?.goodList?: arrayListOf())
                        adapter?.notifyDataSetChanged()
                    }
                    LoadStatus.LOADER_MORE ->{
                        listGood.addAll(contract?.goodList?: arrayListOf())
                        adapter?.notifyDataSetChanged()
                    }
                }
            }
            takeIf { layoutRefresh.isRefreshing }.apply { layoutRefresh.isRefreshing = false }
        })
        return rootview
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        contractId = arguments?.getInt("id") ?: 0
    }

    private fun expandAnimation(expand:Boolean){
        layoutContract.visibility = if(expand){
            View.VISIBLE
        }else{
            View.GONE
        }

    }

    override fun onStart() {
        super.onStart()
        viewModel.getContractDetail(LoadStatus.INIT, contractId)
    }
}