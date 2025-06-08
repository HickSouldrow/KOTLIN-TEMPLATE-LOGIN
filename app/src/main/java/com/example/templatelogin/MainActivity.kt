package com.example.templatelogin

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.templatelogin.ui.theme.TemplateLoginTheme
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextButton
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TemplateLoginTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    NavHost(navController = navController, startDestination = "login") {
                        composable("login") {
                            LoginScreen(
                                onLogin = { userName ->
                                    navController.navigate("home/${userName.ifBlank { "Convidado" }}")
                                },
                                onRegisterClick = {
                                    navController.navigate("register")
                                }
                            )
                        }
                        composable("register") {
                            App(

                            )
                        }
                        composable(
                            "home/{userName}",
                            arguments = listOf(navArgument("userName") {
                                type = NavType.StringType
                            })
                        ) { backStackEntry ->
                            val userName =
                                backStackEntry.arguments?.getString("userName") ?: "Usuário"
                            HomeScreen(userName = userName)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun App() {
    var nome by remember { mutableStateOf("") }
    var apelido by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }
    var mostrarRegistros by remember { mutableStateOf(false) }
    val banco = remember { mutableStateListOf<Map<String, Any>>() }
    val scrollState = rememberScrollState()
    val backgroundColor = Color(0xFF121212)
    val fieldBackground = Color(0xFF1E1E1E)
    val textColor = Color.White
    val labelColor = Color.Gray
    val primaryColor = Color(0xFF5EA500) // Nova cor verde

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(backgroundColor)
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            "Registro Hick1lugar",
            fontFamily = FontFamily.Cursive,
            fontSize = 26.sp,
            color = primaryColor, // Alterado para a nova cor
            modifier = Modifier.padding(vertical = 24.dp)
        )

        CustomDarkTextField(value = nome, onValueChange = { nome = it }, label = "Nome", fieldBackground, textColor, labelColor)
        CustomDarkTextField(value = apelido, onValueChange = { apelido = it }, label = "Nickname", fieldBackground, textColor, labelColor)
        CustomDarkTextField(value = email, onValueChange = { email = it }, label = "E-mail", fieldBackground, textColor, labelColor)
        CustomDarkTextField(value = senha, onValueChange = { senha = it }, label = "Senha", backgroundColor = fieldBackground, textColor = textColor, labelColor = labelColor, isPassword = true)
        CustomDarkTextField(value = telefone, onValueChange = { telefone = it }, label = "Telefone", fieldBackground, textColor, labelColor)

        Button(
            onClick = {
                val banco = hashMapOf(
                    "nome" to nome,
                    "apelido" to apelido,
                    "email" to email,
                    "senha" to senha,
                    "telefone" to telefone,
                )



                nome = ""
                apelido = ""
                email = ""
                senha = ""
                telefone = ""
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            colors = ButtonDefaults.buttonColors(containerColor = primaryColor), // Alterado para a nova cor
            shape = RoundedCornerShape(10.dp)
        ) {
            Text("Cadastrar", fontSize = 18.sp, color = Color.White)
        }

        Button(
            onClick = {
                if (!mostrarRegistros) {
                } else {
                    mostrarRegistros = false
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            colors = ButtonDefaults.buttonColors(containerColor = primaryColor), // Alterado para a nova cor
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(
                if (mostrarRegistros) "Ocultar registros" else "Exibir todos os registros",
                fontSize = 18.sp,
                color = Color.White
            )
        }

        if (mostrarRegistros) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp)
            ) {
                banco.forEachIndexed { index, banco ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .background(Color(0xFF2C2C2C), shape = RoundedCornerShape(8.dp))
                            .padding(12.dp)
                    ) {
                        Text("Registro ${index + 1}", color = primaryColor, fontSize = 18.sp) // Alterado para a nova cor
                        Text("Nome: ${banco["nome"]}", color = Color.White)
                        Text("Apelido: ${banco["apelido"]}", color = Color.White)
                        Text("Email: ${banco["email"]}", color = Color.White)
                        Text("Senha: ${banco["senha"]}", color = Color.White)
                        Text("Telefone: ${banco["telefone"]}", color = Color.White)
                    }
                }

            }
        }
    }
}

@Composable
fun RegisterScreen(onRegisterComplete: () -> Unit) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    val gradient = Brush.verticalGradient(
        colors = listOf(Color(0xFF2196F3), Color(0xFF1976D2))
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = gradient),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(0.9f)
                .wrapContentHeight(),
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(12.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(24.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .height(80.dp)
                        .padding(bottom = 16.dp)
                )

                Text(
                    text = "Cadastro",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1976D2)
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Nome de usuário") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("E-mail") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Senha") },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = { Text("Confirmar senha") },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        onRegisterComplete()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1976D2),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Cadastrar", fontSize = 16.sp)
                }

                Spacer(modifier = Modifier.height(12.dp))

                TextButton(onClick = {
                    onRegisterComplete()
                }) {
                    Text(
                        "Já tem uma conta? Faça login",
                        color = Color(0xFF1976D2),
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

@Composable
fun HomeScreen(userName: String = "Usuário") {
    var menuExpanded by remember { mutableStateOf(false) }

    // Gradiente de fundo vertical
    val gradient = Brush.verticalGradient(
        colors = listOf(Color(0xFF2196F3), Color(0xFF1976D2))
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = gradient),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Botão de menu (três pontos)
            Box {
                IconButton(onClick = { menuExpanded = true }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "Menu",
                        tint = Color.White
                    )
                }

                DropdownMenu(
                    expanded = menuExpanded,
                    onDismissRequest = { menuExpanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Cadastrar Produto") },
                        onClick = {
                            menuExpanded = false
                            // ação de navegação ou lógica
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Listar Produto") },
                        onClick = {
                            menuExpanded = false
                            // ação de navegação ou lógica
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Perfil de $userName") },
                        onClick = {
                            menuExpanded = false
                            // ação de navegação ou lógica
                        }
                    )
                    Divider()
                    DropdownMenuItem(
                        text = { Text("Deslogar") },
                        onClick = {
                            menuExpanded = false
                            // ação de logout
                        }
                    )
                }
            }
        }

        // Card central com boas-vindas
        Card(
            modifier = Modifier
                .padding(top = 80.dp, bottom = 40.dp)
                .fillMaxWidth(0.9f)
                .fillMaxSize(),
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(12.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(24.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .height(80.dp)
                        .padding(bottom = 16.dp)
                )

                Text(
                    text = "Bem-vindo, $userName!",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1976D2),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Use o menu no canto superior direito para navegar.",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun CustomDarkTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    backgroundColor: Color,
    textColor: Color,
    labelColor: Color,
    isPassword: Boolean = false
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = labelColor) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = backgroundColor,
            unfocusedContainerColor = backgroundColor,
            focusedTextColor = textColor,
            unfocusedTextColor = textColor,
            cursorColor = Color(0xFF5EA500), // Alterado para a nova cor
            focusedLabelColor = labelColor,
            unfocusedLabelColor = labelColor,
            focusedIndicatorColor = Color(0xFF5EA500), // Adicionado - cor do indicador quando focado
            unfocusedIndicatorColor = Color.Gray // Adicionado - cor do indicador quando não focado
        )
    )
}

@Composable
fun LoginScreen(onLogin: (String) -> Unit, onRegisterClick: () -> Unit) {
    var user by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Gradiente de fundo vertical
    val gradient = Brush.verticalGradient(
        colors = listOf(Color(0xFF2196F3), Color(0xFF1976D2))
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = gradient),
        contentAlignment = Alignment.Center
    ) {
        // Card com sombra e arredondamento
        Card(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(0.9f)
                .wrapContentHeight(),
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(12.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(24.dp)
            ) {
                // Logo (substitua pela sua imagem)
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .height(80.dp)
                        .padding(bottom = 16.dp)
                )

                Text(
                    text = "App Login",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1976D2)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Campo de usuário
                OutlinedTextField(
                    value = user,
                    onValueChange = { user = it },
                    label = { Text("Usuário") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Campo de senha
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Senha") },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Botão Logar
                Button(
                    onClick = {
                        onLogin(user)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1976D2),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Logar", fontSize = 16.sp)
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Botão Esqueceu a senha
                TextButton(onClick = {
                    onRegisterClick()
                }) {
                    Text(
                        "Esqueceu a senha?",
                        color = Color(0xFF1976D2),
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun AppPreview() {
    TemplateLoginTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            App()
        }
    }
}