package com.example.dictonaryapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dictonaryapp.databinding.MeaningRecyclerRowBinding

class meaningAdapter(private var meaningList : List<Meaning>) : RecyclerView.Adapter<meaningAdapter.MeaningViewHolder>() {
    class MeaningViewHolder (private val binding: MeaningRecyclerRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind (meaning: Meaning) {
            binding.partOfSpeechTextView.text = meaning.partOfSpeech
            binding.definitionsTextView.text = meaning.definitions.joinToString("\n\n"){
                var currIndex = meaning.definitions.indexOf(it)
                (currIndex + 1).toString() + ". " + it.definition.toString()
            }

            binding.synonymsTextView.text = meaning.synonyms.joinToString (", ")
            binding.antonymsTextView.text = meaning.antonyms.joinToString (", ")

            if (meaning.synonyms.isEmpty()) {
                binding.synonymTitleTextView.visibility = View.GONE
                binding.synonymsTextView.visibility = View.GONE
            }

            if (meaning.antonyms.isEmpty()) {
                binding.antonymTitleTextView.visibility = View.GONE
                binding.antonymsTextView.visibility = View.GONE
            }
        }
    }

    fun updateNewData (newMeaningList : List<Meaning>) {
        meaningList = newMeaningList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeaningViewHolder {
        val binding = MeaningRecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MeaningViewHolder(binding)
    }

    override fun getItemCount(): Int = meaningList.size

    override fun onBindViewHolder(holder: MeaningViewHolder, position: Int) = holder.bind(meaningList[position])
}