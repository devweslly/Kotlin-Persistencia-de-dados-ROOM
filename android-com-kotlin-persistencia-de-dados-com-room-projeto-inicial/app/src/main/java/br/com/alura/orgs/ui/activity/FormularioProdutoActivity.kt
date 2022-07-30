package br.com.alura.orgs.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.alura.orgs.database.AppDatabase
import br.com.alura.orgs.databinding.ActivityFormularioProdutoBinding
import br.com.alura.orgs.extensions.tentaCarregarImagem
import br.com.alura.orgs.model.Produto
import br.com.alura.orgs.ui.dialog.FormularioImagemDialog
import java.math.BigDecimal

class FormularioProdutoActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityFormularioProdutoBinding.inflate(layoutInflater)
    }
    private var url: String? = null
    private var idProduto = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        title = "Cadastrar produto"
        configuraBotaoSalvar()
        binding.activityFormularioProdutoImagem.setOnClickListener {
            FormularioImagemDialog(this)
                .mostra(url) { imagem ->
                    url = imagem
                    binding.activityFormularioProdutoImagem.tentaCarregarImagem(url)
                }
        }

        // Mostrar as infos para editar
        // ?.let garante que só irá chamar o código se não for nulo
        intent.getParcelableExtra<Produto>(CHAVE_PRODUTO)?.let { produtoCarregado ->
            // Título da AppBar
            title = "Alterar Produto"
            // Identificando se o produto é novo ou não verificando pelo ID (se for 0 é um valor novo)
            // Se for um valor diferente, terá a decisão de alterar
            idProduto = produtoCarregado.id
            url = produtoCarregado.imagem // Indica que a url é a imagem do produtoCarregado
            // Carregando as infos no formulário a partir do produtoCarregado
            binding.activityFormularioProdutoImagem.tentaCarregarImagem(produtoCarregado.imagem)
            binding.activityFormularioProdutoNome.setText(produtoCarregado.nome)
            binding.activityFormularioProdutoDescricao.setText(produtoCarregado.descricao)
            binding.activityFormularioProdutoValor.setText(produtoCarregado.valor.toPlainString())
        }
    }

    // Inserção no banco de dados e apresentar na lista de produtos
    private fun configuraBotaoSalvar() {
        val botaoSalvar = binding.activityFormularioProdutoBotaoSalvar
        // Criar banco de dados
        // db (instancia de ProdutoDao) vai dar acesso ao Dao e aos comportamentes de buscar e salvar
        val db = AppDatabase.instancia(this) // Acessando o banco de dado por meio da fun instancia
        val produtoDao = db.produtoDao() // Acessando o Dao
        botaoSalvar.setOnClickListener {
            // Cria produto novo ao clicar em salvar
            val produtoNovo = criaProduto()
            // Lógica que decidi se vai alterar ou salvar um produto considerando o valor do id do produto
            if (idProduto > 0) {
                // Se o idProduto > 0 é pq o produto já existe
                produtoDao.altera(produtoNovo)
            } else {
                produtoDao.salva(produtoNovo) // Recebendo referencia de produtoNovo para salvar no banco
            }
            finish()
        }
    }

    private fun criaProduto(): Produto {
        val campoNome = binding.activityFormularioProdutoNome
        val nome = campoNome.text.toString()
        val campoDescricao = binding.activityFormularioProdutoDescricao
        val descricao = campoDescricao.text.toString()
        val campoValor = binding.activityFormularioProdutoValor
        val valorEmTexto = campoValor.text.toString()
        val valor = if (valorEmTexto.isBlank()) {
            BigDecimal.ZERO
        } else {
            BigDecimal(valorEmTexto)
        }

        return Produto(
            id = idProduto,
            nome = nome,
            descricao = descricao,
            valor = valor,
            imagem = url
        )
    }

}