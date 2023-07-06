package com.kost4n.testandroidapp.roof.fragments.stopwatch

import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Chronometer
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.kost4n.testandroidapp.databinding.FragmentStopwatchBinding

class StopwatchFragment : Fragment() {
    private var _binding: FragmentStopwatchBinding? = null

    private val binding get() = _binding!!

    private lateinit var chronometer: Chronometer

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val stopwatchViewModel =
            ViewModelProvider(this).get(StopwatchViewModel::class.java)
        _binding = FragmentStopwatchBinding.inflate(inflater, container, false)
        val root: View = binding.root

        chronometer = binding.chronometer
        chronometer.setOnChronometerTickListener {
            val elapsedMillis: Long = (SystemClock.elapsedRealtime() - chronometer.base)
            if (elapsedMillis > 5000 && elapsedMillis < 6000) {
                val strElapsedMillis = "More than 5 seconds have passed"
//                Toast.makeText(
//                    context,
//                    strElapsedMillis, Toast.LENGTH_SHORT
//                ).show()
            }
        }

        binding.startButton.setOnClickListener {
            chronometer.base = SystemClock.elapsedRealtime()
            chronometer.start()
        }

        binding.stopButton.setOnClickListener {
            chronometer.stop()
        }

        binding.resetButton.setOnClickListener {
            chronometer.base = SystemClock.elapsedRealtime()
        }

            return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}