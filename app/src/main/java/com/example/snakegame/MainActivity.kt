package com.example.snakegame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                SnakeGameApp()
            }
        }
    }
}

data class Point(val x: Int, val y: Int)

@Composable
fun SnakeGameApp() {
    val gridSize = 20
    var snake by remember { mutableStateOf(listOf(Point(10, 10), Point(9, 10), Point(8, 10))) }
    var food by remember { mutableStateOf(Point(15, 15)) }
    var direction by remember { mutableStateOf(Point(1, 0)) }
    var nextDirection by remember { mutableStateOf(Point(1, 0)) }
    var score by remember { mutableStateOf(0) }
    var gameOver by remember { mutableStateOf(false) }
    var gameStarted by remember { mutableStateOf(false) }
    var lastSwipeTime by remember { mutableStateOf(0L) }

    LaunchedEffect(gameStarted, gameOver) {
        if (gameStarted && !gameOver) {
            while (true) {
                delay(150)
                direction = nextDirection

                val head = snake.first()
                val newHead = Point(
                    (head.x + direction.x + gridSize) % gridSize,
                    (head.y + direction.y + gridSize) % gridSize
                )

                if (snake.contains(newHead)) {
                    gameOver = true
                } else {
                    var newSnake = listOf(newHead) + snake
                    if (newHead == food) {
                        score += 10
                        food = Point(Random.nextInt(gridSize), Random.nextInt(gridSize))
                    } else {
                        newSnake = newSnake.dropLast(1)
                    }
                    snake = newSnake
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1a1a1a))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("SNAKE GAME", fontSize = 28.sp, color = Color.Green, modifier = Modifier.padding(vertical = 8.dp))
        Text("Score: $score", fontSize = 20.sp, color = Color.White)
        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .size(300.dp)
                .background(Color(0xFF000000))
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        val now = System.currentTimeMillis()
                        if (now - lastSwipeTime > 100) {
                            lastSwipeTime = now
                            when {
                                dragAmount.x > 30 && direction.x == 0 -> nextDirection = Point(1, 0)
                                dragAmount.x < -30 && direction.x == 0 -> nextDirection = Point(-1, 0)
                                dragAmount.y > 30 && direction.y == 0 -> nextDirection = Point(0, 1)
                                dragAmount.y < -30 && direction.y == 0 -> nextDirection = Point(0, -1)
                            }
                            change.consume()
                        }
                    }
                }
        ) {
            val cellSize = 15.dp

            snake.forEach { segment ->
                Box(
                    modifier = Modifier
                        .size(cellSize)
                        .background(Color.Green)
                        .padding(start = (cellSize * segment.x), top = (cellSize * segment.y))
                )
            }

            Box(
                modifier = Modifier
                    .size(cellSize)
                    .background(Color.Red)
                    .padding(start = (cellSize * food.x), top = (cellSize * food.y))
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (!gameStarted) {
            Button(onClick = {
                gameStarted = true
                gameOver = false
            }, modifier = Modifier.fillMaxWidth(0.8f)) {
                Text("Start Game", fontSize = 18.sp)
            }
        } else if (gameOver) {
            Text("Game Over! Final Score: $score", fontSize = 18.sp, color = Color.Red)
            Button(
                onClick = {
                    snake = listOf(Point(10, 10), Point(9, 10), Point(8, 10))
                    food = Point(15, 15)
                    direction = Point(1, 0)
                    nextDirection = Point(1, 0)
                    score = 0
                    gameOver = false
                    gameStarted = true
                },
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                Text("Restart", fontSize = 18.sp)
            }
        } else {
            Text("Swipe to move", fontSize = 14.sp, color = Color.Gray)
        }
    }
}