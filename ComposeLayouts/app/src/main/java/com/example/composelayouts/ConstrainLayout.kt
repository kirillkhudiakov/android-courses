package com.example.composelayouts

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.atLeast
import com.example.composelayouts.ui.theme.LayoutsCodelabTheme

@Composable
fun ConstraintLayoutContent() = ConstraintLayout {
    val (button1, button2, text) = createRefs()

    Button(
            onClick = { /*TODO*/ },
            Modifier.constrainAs(button1) {
                top.linkTo(parent.top, margin = 16.dp)
            }
    ) {
        Text("Button 1")
    }

    Text("Text", modifier = Modifier.constrainAs(text) {
        top.linkTo(button1.bottom, margin = 16.dp)
        centerHorizontallyTo(parent)
    })

    val barrier = createEndBarrier(button1, text)
    Button(
            onClick = { /*TODO*/ },
            Modifier.constrainAs(button2) {
                top.linkTo(parent.top, margin = 16.dp)
                start.linkTo(barrier)
            }
    ) {
        Text(text = "Button 2")
    }
}

@Preview
@Composable
fun ConstraintLayoutContentPreview() {
    LayoutsCodelabTheme {
        ConstraintLayoutContent()
    }
}

@Composable
fun LargeConstraintLayout() = ConstraintLayout {
    val text = createRef()
    val guideline = createGuidelineFromStart(fraction = 0.5f)
    Text(
            "This is a very very very very very very very long text",
            Modifier.constrainAs(text) {
                linkTo(start = guideline, end = parent.end)
                width = Dimension.preferredWrapContent.atLeast(100.dp)
            }
    )
}

@Preview
@Composable
fun LargeConstraintLayoutPreview() {
    LayoutsCodelabTheme {
        LargeConstraintLayout()
    }
}

@Composable
fun DecoupledConstraintLayout() = BoxWithConstraints {
    val constraints = decoupledConstraints(margin = if (maxWidth < maxHeight) 16.dp else 32.dp)
//    val constraints = decoupledConstraints(margin = (if (maxWidth < maxHeight) 16 else 32).dp)

    ConstraintLayout(constraintSet = constraints) {
        Button(onClick = { /*TODO*/ }, modifier = Modifier.layoutId("button")) {
            Text(text = "Button")
        }
        
        Text("Text", Modifier.layoutId("text"))
    }
}

private fun decoupledConstraints(margin: Dp): ConstraintSet = ConstraintSet {
    val button = createRefFor("button")
    val text = createRefFor("text")

    constrain(button) {
        top.linkTo(parent.top, margin = margin)
    }
    constrain(text) {
        top.linkTo(button.bottom, margin = margin)
    }
}

@Preview
@Composable
fun DecoupledConstraintLayoutPreview() {
    LayoutsCodelabTheme {
        DecoupledConstraintLayout()
    }
}
