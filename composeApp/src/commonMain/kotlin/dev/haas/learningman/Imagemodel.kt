package dev.haas.learningman

import kotlinx.serialization.Serializable

@Serializable
class Imagemodel(
    val category:String?,
    val path:String?,
    val author:String?,
)
