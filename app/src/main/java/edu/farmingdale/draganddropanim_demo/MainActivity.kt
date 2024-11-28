package edu.farmingdale.draganddropanim_demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import edu.farmingdale.draganddropanim_demo.ui.theme.DragAndDropAnim_DemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            DragAndDropAnim_DemoTheme {
                DragAndDropBoxes()
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(text = "Hey Evan, are you going to the cat cafe?!!", modifier = modifier)
}
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DragAndDropAnim_DemoTheme {
        Greeting("Android")
    }
}


// This should be completed in a group setting
// ToDo 1: Analyze the requirements for Individual Project 3 - DONE
// ToDo 2: Show the DragAndDropBoxes composable - DONE
// ToDo 3: Change the circle to a rect - DONE
// ToDo 4: Replace the command right with a image or icon - DONE
// ToDo 5: Make this works in landscape mode only - DONE -- android:screenOrientation="landscape"
// ToDo 6: Rotate the rect around itself - done -- var rotationAngle by remember { mutableIntStateOf(0) }
// ToDo 7: Move - translate the rect horizontally and vertically - done
// ToDo 8: Add a button to reset the rect to the center of the screen - done
// ToDo 9: Enable certain animation based on the drop event (like up or down) - done
// ToDo 10: Make sure to commit for each one of the above and submit this individually