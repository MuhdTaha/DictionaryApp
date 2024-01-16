package com.example.dictonaryapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dictonaryapp.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import java.lang.Exception


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var adapter: meaningAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.searchInput.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                (event != null && event.action == KeyEvent.ACTION_DOWN &&
                        event.keyCode == KeyEvent.KEYCODE_ENTER)
            ) {
                val word = binding.searchInput.text.toString()
                getMeaning(word)
                return@setOnEditorActionListener true
            }
            false
        }

        binding.searchBtn.setOnClickListener{
            val word = binding.searchInput.text.toString()
            getMeaning(word)
        }

        adapter = meaningAdapter(emptyList())
        binding.meaningRecView.layoutManager = LinearLayoutManager(this)
        binding.meaningRecView.adapter = adapter
    }

    private fun getMeaning(word : String) {
        setInProgress(true)
        GlobalScope.launch {
            try {
                val response = RetrofitInstance.dictionaryApi.getMeaning(word)
                if(response.body() == null){
                    throw (Exception())
                }
                runOnUiThread {
                    setInProgress(false)
                    response.body()?.first()?.let {
                        setUI(it)
                    }
                }
            } catch (e : Exception){
                runOnUiThread{
                    setInProgress(false)
                    Toast.makeText(applicationContext,"Something went wrong!",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setUI(response: wordResult) {
        binding.wordTextView.text = response.word
        binding.phoneticTextView.text = response.phonetic
        adapter.updateNewData(response.meanings)
    }

    private fun setInProgress(inProgress: Boolean) {
        if (inProgress) {
            binding.searchBtn.visibility = View.INVISIBLE
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.searchBtn.visibility = View.VISIBLE
            binding.progressBar.visibility = View.INVISIBLE
        }
    }
}