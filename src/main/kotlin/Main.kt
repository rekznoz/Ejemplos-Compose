import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun ecuacion(expression: String): Double {
    // Expresión regular para separar números y operadores (+, -, *, /)
    val regex = Regex("[+\\-*/]")

    // Obtener una lista de números de la expresión
    val numbers = regex.split(expression).map { it.toDouble() }

    // Obtener una lista de operadores de la expresión
    val operators = regex.findAll(expression).map { it.value }.toList()

    // Validar si la cantidad de números y operadores es válida
    require(numbers.size - operators.size == 1) { "Expresión no válida" }

    // Evaluar la expresión
    var result = numbers[0]
    for (i in operators.indices) {
        when (operators[i]) {
            "+" -> result += numbers[i + 1]
            "-" -> result -= numbers[i + 1]
            "*" -> result *= numbers[i + 1]
            "/" -> {
                require(numbers[i + 1] != 0.0) { "División por cero no permitida" }
                result /= numbers[i + 1]
            }
        }
    }

    return result
}

@Composable
fun Contador() {
    var contador by remember { mutableStateOf(0) }
    val texto by remember { mutableStateOf("Click para Contar!") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(1.dp), // Ajustar la separación vertical
        horizontalAlignment = Alignment.Start // Ajustar la alineación horizontal
    ) {
        Button(
            onClick = { contador++ },
            modifier = Modifier.padding(16.dp).fillMaxWidth()
        ) {
            if (contador == 0) {
                Text(texto)
            } else {
                Text("Contador: $contador")
            }
        }
        Button(
            onClick = { contador = 0 },
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            enabled = (contador > 0)
        ) {
            Text("Reset")
        }
    }
}

@Composable
fun Calculadora() {
    var resultado by remember { mutableStateOf("") }
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(1.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(resultado, modifier = Modifier.padding(5.dp).fillMaxWidth())
        // PRIMERA FILA
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Button(onClick = { resultado += "7" }) {
                Text("7")
            }
            Button(onClick = { resultado += "8" }) {
                Text("8")
            }
            Button(onClick = { resultado += "9" }) {
                Text("9")
            }
            Button(onClick = { resultado += "/" }) {
                Text("/")
            }
        }
        // SEGUNDA FILA
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Button(onClick = { resultado += "4" }) {
                Text("4")
            }
            Button(onClick = { resultado += "5" }) {
                Text("5")
            }
            Button(onClick = { resultado += "6" }) {
                Text("6")
            }
            Button(onClick = { resultado += "*" }) {
                Text("X")
            }
        }
        // TERCERA FILA
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Button(onClick = { resultado += "1" }) {
                Text("1")
            }
            Button(onClick = { resultado += "2" }) {
                Text("2")
            }
            Button(onClick = { resultado += "3" }) {
                Text("3")
            }
            Button(onClick = { resultado += "-" }) {
                Text("-")
            }
        }
        // CUARTA FILA
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Button(onClick = { resultado = "" }) {
                Text("C")
            }
            Button(onClick = { resultado += "0" }) {
                Text("0")
            }
            Button(onClick = { resultado += "." }) {
                Text(".")
            }
            Button(onClick = { resultado += "+" }) {
                Text("+")
            }
        }
        // QUINTA FILA
        // BOTON CALCULAR
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Button(onClick = {
                try {
                    resultado = ecuacion(resultado).toString()
                } catch (e: Exception) {
                    resultado = "Error"
                }
            }) {
                Text("=")
            }
        }
    }
}

@Composable
fun StudentList(
    students: List<String>,
    onButtonClick: () -> Unit,
    studentName: String,
    onStudentNameChange: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        for (student in students) {
            Text(
                text = student,
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(10.dp)
            )
        }
        TextField(
            value = studentName,
            onValueChange = onStudentNameChange
        )
        Button(
            onClick = onButtonClick,
            enabled = studentName.isNotBlank()
        ) {
            Text(text = "Add new student")
        }
    }
}

@Composable
@Preview
fun App() {
    val studentsState = remember { mutableStateListOf("Esther", "Jaime") }
    val newStudentState = remember { mutableStateOf("") }
    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background,
        ) {
            //Contador()
            Calculadora()

            /*
            StudentList(
                studentsState,
                { studentsState.add(newStudentState.value) },
                newStudentState.value,
                { newStudent -> newStudentState.value = newStudent }
            )
             */
        }
    }
}


fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
