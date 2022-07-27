package br.com.alura.orgs.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.alura.orgs.database.converter.Converters
import br.com.alura.orgs.database.dao.ProdutoDao
import br.com.alura.orgs.model.Produto

// Tod0 componente do tipo Database tem que herdar da classe RoomDatabase()
// É preciso colacar a notação @Database com os parametros entities e version
// Esta anotação serve para que o Room utilizando o annotation processor consiga gerar o code.
// O entities serve para determinar quais foram as entidades que configuramos dentro do projeto,
// que o Room vai conseguir configurar se comunicar com o SQLite. Neste caso só temos um produto
// e vamos declará-lo dentro do entities. E sempre é esperado um array, entao tem que mandar como array
// e é preciso mandar como uma referência de classe.
// O version recebe um número Int e é basicamente a versão do banco de dado (que implica na técnica de migração,
// que ajusta o banco de dado de conforme mudanças). A classe Produto é a primeira versão do banco de dados
@Database(entities = [Produto::class], version = 1)
@TypeConverters(Converters::class) // referência da classe de conversão
abstract class AppDatabase : RoomDatabase() {
    // Função para o database gerar o código do nosso DAO
    // Use o nome do DAO que vc quer e devolva uma referencia do DAO que está querendo criar
    abstract fun produtoDao(): ProdutoDao
}