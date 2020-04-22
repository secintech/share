import java.nio.ByteBuffer
import kotlin.random.Random

const val WINDOW_SIZE = 100
const val SAMPLE_SIZE = 200

val stairs = Stairs(100)
val buffer = arrayListOf<Byte>()
var vsize = 0

fun main(){

    for (i in 0..SAMPLE_SIZE) {
        vsize = 0
        generateSequence {
            buffer.add(it)
            if (buffer.size == WINDOW_SIZE) {
                stairs.add(ByteBuffer.wrap(buffer.toByteArray()), 1) {
                    printStairs(it)
                }
                buffer.clear()
            }
        }

        println("\nstairs vector size: $vsize")
    }
}

fun generateSequence(size: Int = 1000000, consume: (b: Byte) -> Unit){
    for(i in 0..size)
        consume(Random.nextInt(0,4).toByte())
}

fun printStairs(v: Array<Float>) {
    v.forEach { print("$it ") }
    vsize += v.size
}
