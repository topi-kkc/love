package com.vivien.love.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.math.*

@Composable
fun CubeAnimation() {
    val characters = "VIVIEN"
    var currentCharIndex by remember { mutableStateOf(0) }
    var rotationX by remember { mutableStateOf(0f) }
    var rotationY by remember { mutableStateOf(0f) }
    var rotationZ by remember { mutableStateOf(0f) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            currentCharIndex = (currentCharIndex + 1) % characters.length
            rotationX = (rotationX + 90) % 360
            rotationY = (rotationY + 45) % 360
            rotationZ = (rotationZ + 30) % 360
        }
    }

    Box(
        modifier = Modifier
            .size(300.dp)
            .drawBehind {
                drawCube(
                    rotationX = rotationX,
                    rotationY = rotationY,
                    rotationZ = rotationZ,
                    character = characters[currentCharIndex]
                )
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = characters[currentCharIndex].toString(),
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF00D9FF),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

private fun DrawScope.drawCube(
    rotationX: Float,
    rotationY: Float,
    rotationZ: Float,
    character: Char
) {
    val size = 100f
    val centerX = this.size.width / 2
    val centerY = this.size.height / 2

    val vertices = arrayOf(
        floatArrayOf(-size, -size, -size),
        floatArrayOf(size, -size, -size),
        floatArrayOf(size, size, -size),
        floatArrayOf(-size, size, -size),
        floatArrayOf(-size, -size, size),
        floatArrayOf(size, -size, size),
        floatArrayOf(size, size, size),
        floatArrayOf(-size, size, size)
    )

    val rotatedVertices = vertices.map { vertex ->
        rotateVertex(vertex, rotationX, rotationY, rotationZ)
    }

    val edges = arrayOf(
        intArrayOf(0, 1), intArrayOf(1, 2), intArrayOf(2, 3), intArrayOf(3, 0),
        intArrayOf(4, 5), intArrayOf(5, 6), intArrayOf(6, 7), intArrayOf(7, 4),
        intArrayOf(0, 4), intArrayOf(1, 5), intArrayOf(2, 6), intArrayOf(3, 7)
    )

    edges.forEach { edge ->
        val v1 = rotatedVertices[edge[0]]
        val v2 = rotatedVertices[edge[1]]

        val x1 = centerX + v1[0] * 0.5f
        val y1 = centerY + v1[1] * 0.5f
        val x2 = centerX + v2[0] * 0.5f
        val y2 = centerY + v2[1] * 0.5f

        drawLine(
            color = Color(0xFF00D9FF),
            start = androidx.compose.ui.geometry.Offset(x1, y1),
            end = androidx.compose.ui.geometry.Offset(x2, y2),
            strokeWidth = 3f
        )
    }

    drawCircle(
        color = Color(0xFFFF1493),
        radius = 15f,
        center = androidx.compose.ui.geometry.Offset(centerX, centerY)
    )
}

private fun rotateVertex(
    vertex: FloatArray,
    angleX: Float,
    angleY: Float,
    angleZ: Float
): FloatArray {
    val radX = angleX * PI.toFloat() / 180f
    val radY = angleY * PI.toFloat() / 180f
    val radZ = angleZ * PI.toFloat() / 180f

    var x = vertex[0]
    var y = vertex[1]
    var z = vertex[2]

    val cosX = cos(radX)
    val sinX = sin(radX)
    val y1 = y * cosX - z * sinX
    val z1 = y * sinX + z * cosX

    y = y1
    z = z1

    val cosY = cos(radY)
    val sinY = sin(radY)
    val x2 = x * cosY + z * sinY
    val z2 = -x * sinY + z * cosY

    x = x2
    z = z2

    val cosZ = cos(radZ)
    val sinZ = sin(radZ)
    val x3 = x * cosZ - y * sinZ
    val y3 = x * sinZ + y * cosZ

    return floatArrayOf(x3, y3, z2)
}