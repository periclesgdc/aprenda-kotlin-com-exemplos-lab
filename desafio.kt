import java.time.LocalDateTime // [Template no Kotlin Playground](https://pl.kotl.in/WcteahpyN)
import java.time.LocalDate

class DataExpiradaException(): RuntimeException()

enum class Nivel {
  BASICO,
  INTERMEDIARIO,
  DIFICIL
}

data class Usuario(val nome: String, val nascimento: LocalDate)

data class ConteudoEducacional(var nome: String, val duracao: Int = 60)

data class Formacao(
  val nome: String,
  val nivel: Nivel,
  var conteudos: List<ConteudoEducacional>,
  val dataFechamentoInscricoes: LocalDateTime
) {
  val inscritos = mutableListOf<Usuario>()

  @Throws(DataExpiradaException::class)
  fun matricular(usuario: Usuario) {
    if (dataFechamentoInscricoes.isBefore(LocalDateTime.now())) throw DataExpiradaException()

    inscritos.add(usuario)
  }
}

fun main() {
  // Criando usuários
  val matheus = Usuario("Matheus", LocalDate.of(1999, 1, 28))
  val abner = Usuario("Abner", LocalDate.of(2004, 6, 15))
  val martha = Usuario("Martha", LocalDate.of(1995, 9, 20))
  val alexia = Usuario("Alexia", LocalDate.of(1992, 2, 7))

  // Criando conteúdos
  val java = ConteudoEducacional("Java")
  val kotlin = ConteudoEducacional("Kotlin", 30)
  val git = ConteudoEducacional("Git", 5)


  // Criando formaçoes
  val fullStackJava = Formacao(
    "Fullstack Java",
    Nivel.INTERMEDIARIO,
    listOf(java, kotlin, git),
    LocalDateTime.of(2023, 11, 22, 11, 0)
  )
  val basicoGit = Formacao(
    "Básico de GIT",
    Nivel.BASICO,
    listOf(git),
    LocalDateTime.of(2023, 10, 15, 23, 0)
  )
  val avancadoKotlin = Formacao(
    "Kotlin Completo",
    Nivel.DIFICIL,
    listOf(kotlin, git),
    LocalDateTime.of(2022, 1, 10, 23, 0)
  )

  // Matriculando alunos nos cursos
  fullStackJava.matricular(matheus)
  fullStackJava.matricular(martha)

  basicoGit.matricular(alexia)
  basicoGit.matricular(abner)

  // Validando data ultrapassada
  try {
    avancadoKotlin.matricular(alexia)
  } catch(e: DataExpiradaException) {
    println("Essse curso não está mais disponível")
  }

  listOf(fullStackJava, basicoGit, avancadoKotlin).forEach {
    println("Formaçao: ${it.nome}")
    println("Alunos: ")
    it.inscritos.forEach { aluno ->
      println("\t${aluno.nome}")
    }
    println()
  }
}
