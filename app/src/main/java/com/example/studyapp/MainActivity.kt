package com.example.studyapp


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val color = remember {
                mutableStateOf(Color.Gray)
            }
            val bcColor = remember {
                mutableStateOf(Color.LightGray)
            }
            val boxesQuantity = remember {
                mutableStateOf(1)
            }
            val hexValue = remember {
                mutableStateOf(Integer.toHexString(color.value.toArgb()).substring(2).uppercase())
            }

            val colorList = remember {
                mutableStateOf(listOf(Color.Gray))
            }

            bcColor.value = Color(
                color.value.component1(),
                color.value.component2(),
                color.value.component3(),
                0.2f
            )

            val redValue = color.value.toArgb().red
            val greenValue = color.value.toArgb().green
            val blueValue = color.value.toArgb().blue

            val rgbValue = remember {
                mutableStateOf("68, 68, 68")
            }

            if (boxesQuantity.value == 1) {
                rgbValue.value = "($redValue, $greenValue, $blueValue)"
                hexValue.value = Integer.toHexString(color.value.toArgb()).substring(2).uppercase()
            }


            val fontColor = Color(
                color.value.toArgb().red - 100,
                color.value.toArgb().green - 100,
                color.value.toArgb().blue - 100,
                255
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(bcColor.value)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {

                ColorBoxes(
                    referenceColor = color.value,
                    boxQuantity = boxesQuantity.value,
                    modifier = Modifier.weight(1f),
                    colorList.value
                ) {
                    hexValue.value = it[0]
                    rgbValue.value = it[1]
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(2f),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "HEX: #${hexValue.value}",
                            textAlign = TextAlign.Left,
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold,
                            color = fontColor
                        )
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(45.dp)
                        )
                        Text(
                            text = "RGB: ${rgbValue.value}",
                            textAlign = TextAlign.Left,
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold,
                            color = fontColor
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        QuantityButton(
                            modifier = Modifier
                                .weight(1f),
                            quantity = boxesQuantity.value,
                            buttonColor = color.value,
                            textColor = fontColor,
                            decreaseQuantity = {
                                boxesQuantity.value = it
                                colorList.value = colorList.value.dropLast(1)
                            },
                            increaseQuantity = {
                                boxesQuantity.value = it
                                colorList.value += getRandomColor(it)
                            }
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        ColorButton(
                            Modifier
                                .weight(1f)
                                .fillMaxHeight(0.5f),
                            mainColor = color.value,
                            textColor = fontColor
                        ) {
                            color.value = it
                            val newColors = MutableList(colorList.value.size) {  int -> getRandomColor(int) }
                            colorList.value = newColors
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun QuantityButton(
    modifier: Modifier,
    quantity: Int,
    buttonColor: Color,
    textColor: Color,
    decreaseQuantity: (Int) -> Unit,
    increaseQuantity: (Int) -> Unit
) {
    val newQuantity = remember { mutableStateOf(0) }

    Row(
        modifier
            .fillMaxHeight(0.5f)
            .background(buttonColor, RoundedCornerShape(32.dp)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(
            onClick = {
                if (quantity > 1){
                    newQuantity.value = quantity - 1
                    decreaseQuantity(newQuantity.value)
                }
            },
            modifier = modifier
                .clip(CircleShape)
                .fillMaxSize(),
            colors = ButtonDefaults.buttonColors(buttonColor),
            elevation = ButtonDefaults.elevation(0.dp, 0.dp)
        ) {
            Text(
                text = "-",
                color = textColor,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Text(
            modifier = modifier,
            text = quantity.toString(),
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = textColor
        )
        Button(
            onClick = {
                if (quantity < 9){
                    newQuantity.value = quantity + 1
                    increaseQuantity(newQuantity.value)
                }
            },
            modifier = modifier
                .clip(CircleShape)
                .fillMaxSize(),
            colors = ButtonDefaults.buttonColors(buttonColor),
            elevation = ButtonDefaults.elevation(0.dp, 0.dp)
        ) {
            Text(
                text = "+",
                color = textColor,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun ColorBoxes(
    referenceColor: Color,
    boxQuantity: Int,
    modifier: Modifier,
    colorList: List<Color>,
    sendColor: (List<String>) -> Unit
) {
    val boxesCounter = remember {
        mutableStateOf(0)
    }
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        colorList.chunked(3).forEach { rowItems ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                when(rowItems.size){
                    2, 5 -> Spacer(modifier = Modifier.weight(0.5f))
                }



                rowItems.forEach { item ->
                    val redValueRedBox = item.toArgb().red
                    val greenValueRedBox = item.toArgb().green
                    val blueValueRedBox = item.toArgb().blue
                    val rgbValueRedBox = "($redValueRedBox, $greenValueRedBox, $blueValueRedBox)"

                    if ((colorList.size == 4  && colorList.indexOf(item) == 3) || ( colorList.size == 7 && colorList.indexOf(item) == 6)){
                        Spacer(modifier = Modifier.weight(1f))
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .padding(6.dp)
                            .background(item, RoundedCornerShape(32.dp))
                            .clickable {
                            sendColor(
                                listOf(
                                    Integer
                                        .toHexString(item.toArgb())
                                        .substring(2)
                                        .uppercase(), rgbValueRedBox
                                )
                            )
                        }
                    )
                    if ((colorList.size == 4  && colorList.indexOf(item) == 3) || ( colorList.size == 7 && colorList.indexOf(item) == 6)){
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
                when(rowItems.size){
                    2, 5 -> Spacer(modifier = Modifier.weight(0.5f))
                }
            }
        }
    }
}

private fun getRandomColor(seed: Int): Color {
    val randomGenerator = Random(System.currentTimeMillis() - (seed * 100))
    return Color(
        (randomGenerator.nextInt(600, 950)) * 0.001f,
        (randomGenerator.nextInt(600, 950)) * 0.001f,
        (randomGenerator.nextInt(600, 950)) * 0.001f,
        1f
    )
}

@Composable
fun ColorButton(
    modifier: Modifier,
    mainColor: Color,
    textColor: Color,
    updateColor: (Color) -> Unit
) {
    val randomGenerator = Random(System.currentTimeMillis())

    Button(
        onClick = {
            updateColor(
                Color(
                    (randomGenerator.nextInt(600, 950)) * 0.001f,
                    (randomGenerator.nextInt(600, 950)) * 0.001f,
                    (randomGenerator.nextInt(600, 950)) * 0.001f,
                    1f
                )
            )
        },
        colors = ButtonDefaults.buttonColors(mainColor),
        shape = RoundedCornerShape(42.dp),
        modifier = modifier
    ) {
        Text(
            text = "Get color",
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = textColor
        )
    }

}
