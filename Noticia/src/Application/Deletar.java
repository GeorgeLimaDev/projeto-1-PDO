package Application;

import java.util.List;

import com.db4o.ObjectContainer;
import com.db4o.query.Query;

import model.Noticia;
import model.Assunto;
import util.Util;

public class Deletar{
    protected ObjectContainer manager;

    public Deletar(){
        manager = Util.conectarBanco();
		this.apagar();
		Util.desconectar();
    }

    public void apagar(){
        /* Apagar o assunto 'Variedades' do banco de dados. */
        Query q = manager.query();
        q.constrain(Assunto.class);
        q.descend("nome").constrain("Variedades");
        List<Assunto> resultado = q.execute();
        
        if( resultado.size() > 0 ) {
            Assunto a = resultado.get(0);
            for(Noticia n : a.getListaNoticia()){
                Util.removeCross(n,a);
                if (n.getListaAssuntos().isEmpty()) 
                    manager.delete(n);	
            }
            Util.deleteFromDatabase(a);
            System.out.println("Deletado!");

        }else
        System.out.println("inexistente");
    }

    public static void main(String[] args) {
    	new Deletar();

    } 
}