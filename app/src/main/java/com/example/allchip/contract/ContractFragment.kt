package com.example.allchip.contract

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.allchip.R
import com.example.allchip.data.LoadStatus
import com.example.allchip.data.model.Contract
import androidx.navigation.fragment.findNavController


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ContractFragment : Fragment() {
    private lateinit var layoutRefresh: SwipeRefreshLayout
    private lateinit var listContract: RecyclerView
    private var viewModel: ContractViewModel? = null

    private var adapter: ContractAdapter? = null
    private var list = arrayListOf<Contract>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProviders.of(this, ContractViewModelFactory())
            .get(ContractViewModel::class.java)
        val rootView = inflater.inflate(R.layout.fragment_first, container, false)
        layoutRefresh = rootView.findViewById(R.id.layout_refresh)
        listContract = rootView.findViewById(R.id.list_contract)

        layoutRefresh.setOnRefreshListener {
            viewModel?.getContractList(LoadStatus.REFRESH)
        }

        viewModel?.contractList?.observe(viewLifecycleOwner, Observer {
            if (adapter == null) {
                adapter = ContractAdapter(list)
                adapter?.setOnItemClickListener { view, index ->
                    val bundle = Bundle()
                    bundle.putInt("id", list[index].id)
                    findNavController().navigate(
                        R.id.action_FirstFragment_to_SecondFragment,
                        bundle
                    )
                }

            }
            listContract.layoutManager = LinearLayoutManager(activity)
            listContract.adapter = adapter
            if (it.isSucces()) {
                when (it.data?.status) {
                    LoadStatus.INIT, LoadStatus.REFRESH -> {
                        list.clear()
                        list.addAll(it.data.data ?: arrayListOf())
                        adapter?.notifyDataSetChanged()
                    }
                    LoadStatus.LOADER_MORE -> {
                        list.addAll(it.data.data ?: arrayListOf())
                        adapter?.notifyDataSetChanged()
                    }
                }
            }
            takeIf {
                layoutRefresh.isRefreshing
            }.apply { layoutRefresh.isRefreshing = false }
        })
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onStart() {
        super.onStart()
        viewModel?.getContractList(LoadStatus.INIT)
    }

}