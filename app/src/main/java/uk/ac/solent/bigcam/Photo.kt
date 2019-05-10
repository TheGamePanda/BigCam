package uk.ac.solent.bigcam


class Photo (i: String, l: String, a: String) {

    // Attributes
    val id: String
    var location: String
    var album: String

    // Init block, for performing tasks on creation of the object
    init {
        id = i
        location = l
        album = a
    }
}