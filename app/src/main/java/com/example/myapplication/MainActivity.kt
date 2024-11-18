package com.example.myapplication

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() { //här skapas huvudaktiviteten för appen

    private var currentQuestionIndex = 0 //håller koll på vilken fråga vi är på just nu
    private var score = 0 //sparar antalet rätt svar



    private val questions = listOf( //lista med frågor
        Question("What is 2 + 2?", "3", "4", "5", "4"),
        Question("What is 3 + 3?", "6", "5", "8", "6"),
        Question("What is 5 + 7?", "12", "10", "11", "12")




    )

    override fun onCreate(savedInstanceState: Bundle?) { //vad som ska ske när appen startas
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) //här laddas layouten för main
        val questionText = findViewById<TextView>(R.id.question_text)
        val answerButton1 = findViewById<Button>(R.id.answer_button_1)
        val answerButton2 = findViewById<Button>(R.id.answer_button_2)
        val answerButton3 = findViewById<Button>(R.id.answer_button_3)
        val scoreText = findViewById<TextView>(R.id.score_text)
        val resultText = findViewById<TextView>(R.id.result_text)

        // Set initial question
        // Otherwise update the question and reset button colors
        // Visa overlay vid starten
        showText() // Visa fullscreen overlay vid start
        Handler(Looper.getMainLooper()).postDelayed({
            hideText() // Dölj overlay efter 5 sekunder

        }, 5000)

        updateQuestion()

        // Define the click listeners
        answerButton1.setOnClickListener {
            checkAnswer(answerButton1, answerButton1.text.toString())
        }

        answerButton2.setOnClickListener {
            checkAnswer(answerButton2, answerButton2.text.toString())
        }

        answerButton3.setOnClickListener {
            checkAnswer(answerButton3, answerButton3.text.toString())
        }
        questionText.setTextColor(resources.getColor(R.color.black))
        resultText.setTextColor(resources.getColor(R.color.black))

        // Update score text
        scoreText.text = "Score: $score"



    }

    private fun checkAnswer(selectedButton: Button, selectedAnswer: String) {
        val correctAnswer = questions[currentQuestionIndex].correctAnswer

        // Change the color of the selected button based on the answer
        if (selectedAnswer == correctAnswer) {
            score++
            selectedButton.setBackgroundColor(resources.getColor(R.color.correctAnswerColor))
        } else {
            selectedButton.setBackgroundColor(resources.getColor(R.color.incorrectAnswerColor))
        }

        // Move to the next question automatically after 1 second
        Handler().postDelayed({
            // Go to next question
            currentQuestionIndex++

            // If there are no more questions, show the result
            if (currentQuestionIndex >= questions.size) {
                showResult()

            } else {
                updateQuestion()
            }
        }, 1000) // Delay for 1 second to show the result before moving to the next question
    }

    private fun updateQuestion() {
        // Reset button colors to the default (no selection)
        val answerButton1 = findViewById<Button>(R.id.answer_button_1)
        val answerButton2 = findViewById<Button>(R.id.answer_button_2)
        val answerButton3 = findViewById<Button>(R.id.answer_button_3)

        answerButton1.setBackgroundColor(resources.getColor(R.color.Button_color))  //bytte färg här till buttoncolor i mer gult
        answerButton2.setBackgroundColor(resources.getColor(R.color.Button_color))
        answerButton3.setBackgroundColor(resources.getColor(R.color.Button_color))

        if (currentQuestionIndex < questions.size) {
            val question = questions[currentQuestionIndex]
            findViewById<TextView>(R.id.question_text).text = question.text
            findViewById<Button>(R.id.answer_button_1).text = question.answer1
            findViewById<Button>(R.id.answer_button_2).text = question.answer2
            findViewById<Button>(R.id.answer_button_3).text = question.answer3
        }
    }

    private fun showResult() {
        findViewById<TextView>(R.id.score_text).visibility = View.GONE
        findViewById<TextView>(R.id.result_text).visibility = View.VISIBLE
        findViewById<TextView>(R.id.result_text).text = "You scored $score out of ${questions.size}."
        // Starta om quizet 5 sekunder efter slut och resultat visats.
        Handler(Looper.getMainLooper()).postDelayed({
            restartQuiz()  //tar en till funktion som startar om quizzet
        }, 5000) // 5000 ms = 5 sekunder
    }

    private fun restartQuiz(){
        currentQuestionIndex = 0;

        //gör så att result det återställs med att resultat och text ej syns igen
        findViewById<TextView>(R.id.result_text).visibility = View.GONE
        score = 0;

        showText()   //visar starttexten
        //
        Handler(Looper.getMainLooper()).postDelayed({
            hideText()  //gömmer texten
            updateQuestion()  //startar om frågorna
        }, 5000)




    }
    //detta är en funktion för att visa "QUIZ START" då quiz startar
    private fun showText() {
        val quiz_Start = findViewById<TextView>(R.id.quiz_start)   //sätter variabeln till något jag definerat i layot som textview
        quiz_Start.visibility = View.VISIBLE   //gör den synlig
    }

    private fun hideText() {
        val quiz_Start = findViewById<TextView>(R.id.quiz_start)
        quiz_Start.visibility = View.GONE
    }
}

data class Question(val text: String, val answer1: String, val answer2: String, val answer3: String, val correctAnswer: String)