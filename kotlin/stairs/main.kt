import java.nio.ByteBuffer

fun generateDNA(size: Int = 1000000, consume: (b: Byte)->Unit){
    var idx =0
    for(i in 0..size) {
        consume(idx.toByte())
        idx = (idx + 1)%4
    }
}

fun main(){
    val SIZE = 100
    var vsize = 0
    val stairs = Stairs(100)
    val buffer = arrayListOf<Byte>()
    generateDNA(){
        buffer.add(it)
        if (buffer.size == SIZE) {
            stairs.add(ByteBuffer.wrap(buffer.toByteArray()), 1) {
                printStairs(it)
                vsize += it.size
            }
            buffer.clear()
        }
    }

   println("\nvector size: $vsize")
}

fun printStairs(v: Array<Float>) {
    v.forEach { print("$it ") }
}

