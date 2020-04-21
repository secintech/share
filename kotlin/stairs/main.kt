import java.nio.ByteBuffer

const val SIZE = 100

var vsize = 0
val stairs = Stairs(100)
val buffer = arrayListOf<Byte>()

fun main(){
    generateDNA(){
        buffer.add(it)
        if (buffer.size == SIZE) {
            stairs.add(ByteBuffer.wrap(buffer.toByteArray()), 1) {
                printStairs(it)
            }
            buffer.clear()
        }
    }

   println("\nvector size: $vsize")
}

fun generateDNA(size: Int = 10000000, consume: (b: Byte)->Unit){
    var idx =0
    for(i in 0..size) {
        consume(idx.toByte())
        idx = (idx + 1)%4
    }
}


fun printStairs(v: Array<Float>) {
    v.forEach { print("$it ") }
    vsize += v.size
}

