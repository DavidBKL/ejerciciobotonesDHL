package com.example.buttonsdhl

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.buttonsdhl.ui.theme.ButtonsDHLTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ButtonsDHLTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    var message by remember { mutableStateOf("") }
    var isCheckboxChecked by remember { mutableStateOf(false) }
    var isSwitchOn by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("Opción 1") }
    var showProgress by remember { mutableStateOf(false) }
    var imageIndex by remember { mutableStateOf(0) }
    val images = listOf(R.drawable.image1, R.drawable.image2, R.drawable.image3)
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Icon
        Icon(
            imageVector = Icons.Filled.Star,
            contentDescription = "Favorite Icon",
            modifier = Modifier.size(40.dp)
        )

        // Button and Progress Indicator
        Button(onClick = {
            coroutineScope.launch {
                message = "Botón presionado"
                showProgress = true
                delay(5000)
                showProgress = false
            }
            imageIndex = (imageIndex + 1) % images.size
        }) {
            Text("Presionar")
        }

        if (showProgress) {
            CircularProgressIndicator()
        }

        // Text Field
        if (isCheckboxChecked) {
            Text(text = message)
        }

        // Checkbox
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isCheckboxChecked,
                onCheckedChange = { isCheckboxChecked = it }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Activar")
        }

        // Switch
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Switch(
                checked = isSwitchOn,
                onCheckedChange = { isSwitchOn = it }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Mostrar opciones")
        }

        // RadioButton Group
        if (isSwitchOn) {
            Column {
                RadioButtonGroup(
                    options = listOf("Opción 1", "Opción 2", "Opción 3"),
                    selectedOption = selectedOption,
                    onOptionSelected = {
                        selectedOption = it
                        message = "Seleccionaste: $it"
                        // Actualizar la imagen basada en la opción seleccionada
                        imageIndex = when (it) {
                            "Opción 1" -> 0
                            "Opción 2" -> 1
                            "Opción 3" -> 2
                            else -> 0
                        }
                    }
                )
            }
        }

        // Imagen que cambia según la opción seleccionada
        Image(
            painter = painterResource(id = images[imageIndex]),
            contentDescription = "Imagen cambiante",
            modifier = Modifier.size(100.dp)
        )
    }
}

@Composable
fun RadioButtonGroup(options: List<String>, selectedOption: String, onOptionSelected: (String) -> Unit) {
    Column {
        options.forEach { option ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = option == selectedOption,
                    onClick = { onOptionSelected(option) }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(option)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ButtonsDHLTheme {
        MainScreen()
    }
}
