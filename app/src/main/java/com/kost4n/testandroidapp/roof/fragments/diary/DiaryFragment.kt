package com.kost4n.testandroidapp.roof.fragments.diary

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.kost4n.testandroidapp.R
import com.kost4n.testandroidapp.databinding.FragmentDiaryBinding
import com.kost4n.testandroidapp.roof.adapter.RecordAdapter
import com.kost4n.testandroidapp.roof.adapter.RecordAdapter.OnRecordClickListener
import com.kost4n.testandroidapp.roof.adapter.RecycleItemDecoration
import com.kost4n.testandroidapp.roof.database.RecordDatabase
import com.kost4n.testandroidapp.roof.entity.Record
import com.kost4n.testandroidapp.roof.entity.Workout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class DiaryFragment : Fragment() {
    private var _binding : FragmentDiaryBinding? = null

    private val binding get() = _binding!!

    private val recordDao by lazy {
        RecordDatabase.getDatabase(requireContext().applicationContext).getRecordDao()
    }
    private val recordDatabase by lazy {
        RecordDatabase.getDatabase(requireContext().applicationContext)
    }
    private lateinit var adapter: RecordAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val diaryViewModel =
            ViewModelProvider(this).get(DiaryViewModel::class.java)
        _binding = FragmentDiaryBinding.inflate(inflater, container, false)
        val root : View = binding.root

        Log.i("onCreateView", "----------------------${recordDatabase.isOpen}------------------------------------")


        observeRecords()
        setRecyclerView()
//
//        diaryViewModel.getList().observe(viewLifecycleOwner, Observer {
//            it?.let {
//                adapter.update(it.toMutableList())
//            }
//        })

        binding.addButton.setOnClickListener {
            createAddDialog(inflater)
            observeRecords()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setRecyclerView() {
        val recyclerView = binding.recyclerViewTraining
        val recordClickListener = object : OnRecordClickListener {
            override fun onRecordClick(record: Record, position: Int) {
                createDeleteDialog(record)
                observeRecords()
            }
        }
        adapter =  RecordAdapter(recordClickListener)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)

        val recycleItemDecorations = RecycleItemDecoration(6)
        recyclerView.addItemDecoration(recycleItemDecorations)

        recyclerView.adapter = adapter
    }

    private fun createDeleteDialog( record: Record) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Are you sure you want to delete?")
        builder.setCancelable(false)
        builder.setPositiveButton("Delete") { _, _ ->
            lifecycleScope.launch(Dispatchers.IO) {
                recordDao.deleteByRecordDate(record.date)
            }
        }

        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }

    fun observeRecords() {
        lifecycleScope.launch {
            recordDao.getRecords().collect {
                if (it.isNotEmpty()) {
                    Log.i("observeRecords", "--------------------${recordDatabase.isOpen}--------------------------------------")
                    adapter.update(it.toMutableList())
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }

    private fun createAddDialog(inflater: LayoutInflater) {
        val builder = AlertDialog.Builder(context)
        val addDialog = inflater.inflate(R.layout.add_record_dialog, null)
        builder.setView(addDialog)

        val workout = addDialog.findViewById<EditText>(R.id.input_work)
        val count = addDialog.findViewById<EditText>(R.id.input_count)

        builder.setCancelable(false)
        builder.setPositiveButton("Add") { _, _ ->


            lifecycleScope.launch {
                val newRecord = Record(Date(), Workout(workout.text.toString(), count.text.toString().toInt()))
                recordDao.addRecord(newRecord)
            }
        }

        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }
}