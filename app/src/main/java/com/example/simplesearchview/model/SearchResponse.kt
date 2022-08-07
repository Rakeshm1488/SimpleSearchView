package com.example.simplesearchview

data class SearchResponse(
    val sf: String,
    val lfs: List<Lfs>
)

data class Lfs(
    val lf: String,
    val freq: Int,
    val since: Int,
    val vars: List<Vars>
)

data class Vars(
    val lf: String,
    val freq: Int,
    val since: Int
)