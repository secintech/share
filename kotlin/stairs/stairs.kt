import java.lang.Math.*
import java.nio.ByteBuffer

val Boolean.ord : Byte
    get(){
        return if (this) 1 else 0
    }

val linearClassifier : (Byte) -> Byte = {(it >= 0).ord}
val discreteClassifier : (Float) -> Byte = {(it > 0).ord}

fun Array<Byte>.asString(sep : String = " "): String {
    var str = this[0].toString()
    for (i in 1 until size)
        str += " " + this[i].toString()

    return str
}

fun Array<Float>.asString(template: String = "%.3f", sep : String = " "): String {
    var str = template.format(this[0])
    for (i in 1 until size)
        str += "$sep$template".format(this[i])

    return str
}


fun Array<Float>.asBin(classify : (Float) -> Byte = discreteClassifier) : Array<Byte>{
    val byte = Array<Byte>(size){0}
    for(i in 0 until this.size)
        byte[i] = classify(this[i])
    return  byte
}

fun Array<Byte>.toBin(classify : (Byte) -> Byte = linearClassifier) : Array<Byte>{
    for(i in 0 until this.size)
        this[i] = classify(this[i])
    return  this
}


fun Array<Byte>.similar(vector: Array<Byte>, threshold : Float = 0.5f) : Byte{
    var sum = 0.0
    val minsize = kotlin.math.min(this.size, vector.size)
    for(i in 0 until minsize)
        sum += (this[i] == vector[i]).ord

    return (sqrt(sum) <= threshold).ord
}

fun Array<Float>.distance(vector: Array<Float>, threshold : Float = 0.5f) : Double{
    var sum = 0.0
    val minsize = kotlin.math.min(this.size, vector.size)
    for(i in 0 until minsize)
        sum += abs(this[i] - vector[i])

    return sum/minsize
}

fun Array<Float>.euclidian(vector: Array<Float>, threshold : Float = 0.5f) : Double{
    var sum = 0.0
    val minsize = kotlin.math.min(this.size, vector.size)
    for(i in 0 until minsize)
        sum += (this[i] - vector[i])*(this[i] - vector[i])

    return sqrt(sum)
}


fun Array<Float>.similar(vector: Array<Float>, threshold : Float = 0.5f) : Byte{
    return (distance(vector) <= threshold).ord
}

class Stairs(var size: Int = 100) {
    private var _data : Array<Float> = Array(size){0.0f}
    val data: Array<Float> = _data
    var idx : Int = 0
    var carry: Float = 0.0f

    fun add(buffer : ByteBuffer, threshold: Int, ready:(data: Array<Float>)->Unit){
        val vector = buffer.array()
        var freq = frequency(vector, 0, vector.size/2, threshold, vector.size.toFloat())
        data[idx] = freq + carry
        carry = frequency(vector, vector.size/2 + 1, vector.size - 1, threshold, vector.size.toFloat())
        idx++

        if (idx == data.size - 1) {
            ready(data.clone())
            clear()
        }
    }

    fun clear() {
        idx = 0
        carry = 0.0f
    }

    private fun frequency(data: ByteArray, start: Int, end: Int, threshold: Int,  total: Float): Float {
        var count = 0
        for(i in start .. end)
            count += (data[i].compareTo(threshold) > 0).ord

        return count/total
    }
}

class FloatStairs(var size: Int = 100) {
    private var _data : Array<Float> = Array(size){0.0f}
    val data: Array<Float> = _data
    var idx : Int = 0
    var carry: Float = 0.0f

    fun add(vector : FloatArray, threshold: Int, ready:(data: Array<Float>)->Unit){
        var freq = frequency(vector, 0, vector.size/2, threshold, vector.size.toFloat())
        data[idx] = freq + carry
        carry = frequency(vector, vector.size/2 + 1, vector.size - 1, threshold, vector.size.toFloat())
        idx++

        if (idx == data.size - 1) {
            ready(data.clone())
            clear()
        }
    }

    fun clear() {
        idx = 0
        carry = 0.0f
    }

    private fun frequency(data: FloatArray, start: Int, end: Int, threshold: Int,  total: Float): Float {
        var count = 0
        for(i in start .. end)
            count += (data[i].compareTo(threshold) > 0).ord

        return count/total
    }
}
