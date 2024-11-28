@file:OptIn(ExperimentalFoundationApi::class)

package edu.farmingdale.draganddropanim_demo

import android.content.ClipData
import android.content.ClipDescription
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.draganddrop.dragAndDropSource
import androidx.compose.foundation.draganddrop.dragAndDropTarget
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draganddrop.DragAndDropEvent
import androidx.compose.ui.draganddrop.DragAndDropTarget
import androidx.compose.ui.draganddrop.DragAndDropTransferData
import androidx.compose.ui.draganddrop.mimeTypes
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.unit.dp
import java.nio.file.Files.size


@Composable
fun DragAndDropBoxes(modifier: Modifier = Modifier) {
    Column(modifier = Modifier.fillMaxSize()) {
        var dragBoxIndex by remember {mutableIntStateOf(0)}
        var userInteracted by remember { mutableStateOf(false) } // Tracks user interaction

        Row(
            modifier = modifier
                .fillMaxWidth().weight(0.2f)
        ) {
            val boxCount = 4

            repeat(boxCount) { index ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(10.dp)
                        .border(1.dp, Color.Black)
                        .dragAndDropTarget(
                            shouldStartDragAndDrop = { event ->
                                event
                                    .mimeTypes()
                                    .contains(ClipDescription.MIMETYPE_TEXT_PLAIN)
                            },
                            target = remember {
                                object : DragAndDropTarget {
                                    override fun onDrop(event: DragAndDropEvent): Boolean {
                                        dragBoxIndex = index
                                        userInteracted = true
                                        return true
                                    }
                                }
                            }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    this@Row.AnimatedVisibility(
                        visible = index == dragBoxIndex,
                        enter = scaleIn() + fadeIn(),
                        exit = scaleOut() + fadeOut()
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = "",
                            modifier = Modifier
                                .fillMaxSize()
                                .dragAndDropSource {
                                    detectTapGestures(
                                        onLongPress = { offset ->
                                            startTransfer(
                                                transferData = DragAndDropTransferData(
                                                    clipData = ClipData.newPlainText(
                                                        "text",
                                                        ""
                                                    )
                                                )
                                            )
                                        }
                                    )
                                }
                        )
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.8f)
                .background(Color.Red)
        ) {
            var rotationAngle by remember { mutableIntStateOf(0) }
            var offsetX by remember { androidx.compose.runtime.mutableFloatStateOf(0f) }
            var offsetY by remember { androidx.compose.runtime.mutableFloatStateOf(0f) }
            var canvasCenter by remember { mutableStateOf(Offset.Zero) }
            var isInitialized by remember { mutableStateOf(false) }

            Button(
                onClick = {
                    offsetX = canvasCenter.x
                    offsetY = canvasCenter.y
                },
                modifier = Modifier.align(Alignment.TopCenter).padding(16.dp)
            ) {
                Text("Reset")
            }

            Canvas(modifier = Modifier.fillMaxSize()) {
                val canvasWidth = size.width
                val canvasHeight = size.height

                canvasCenter = Offset(
                    x = (canvasWidth - 100f) / 2,
                    y = (canvasHeight - 50f) / 2
                )

                if (!isInitialized) {
                    offsetX = canvasCenter.x
                    offsetY = canvasCenter.y
                    isInitialized = true
                }

                if (userInteracted) {
                    when (dragBoxIndex) {
                        0 -> offsetY = (offsetY - 10f).coerceAtLeast(0f) // Move Up
                        1 -> offsetY = (offsetY + 10f).coerceAtMost(canvasHeight - 50f) // Move Down
                        2 -> offsetX = (offsetX - 10f).coerceAtLeast(0f) // Move Left
                        3 -> offsetX = (offsetX + 10f).coerceAtMost(canvasWidth - 100f) // Move Right
                    }
                }

                val rectWidth = 100f
                val rectHeight = 50f

                translate(left = offsetX, top = offsetY) {
                    rotate(
                        degrees = rotationAngle.toFloat(),
                        pivot = Offset(rectWidth / 2, rectHeight / 2) // Rotate rect around its center
                    ) {
                        drawRect(
                            color = Color.Green,
                            topLeft = Offset.Zero,
                            size = Size(width = rectWidth, height = rectHeight)
                        )
                    }
                }
                rotationAngle = (rotationAngle + 5) % 360
            }
        }
    }
}