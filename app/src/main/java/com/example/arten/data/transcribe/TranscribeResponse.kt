package com.example.arten.data.transcribe

import com.google.gson.annotations.SerializedName

data class TranscribeResponse(val transcription: String, val stats: Any, val filename: String)

data class TranscribeStatsResponse(
    @SerializedName("total_processing_time") val totalProcessingTime: Int,
    @SerializedName("words_per_second") val wordsPerSecond: Int,
    @SerializedName("file_size_in_bytes") val fileSizeInBytes: Int,
)