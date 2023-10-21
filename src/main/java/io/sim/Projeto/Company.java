package io.sim.Projeto;

import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Company extends Thread {
  /*public static void main(String[] args) {
    Company company = new Company();

    company.getRotas();

    company.start();

  }*/
        // conjunto de rotas
        private ArrayList<Route> rotas = new ArrayList<>();
        private ArrayList<Route> rotasExecucao = new ArrayList<>();
        private ArrayList<Route> rotasExecutadas = new ArrayList<>();

        //construtor da classe com as rotas
        public Company() {
		      try {
            //aquisicao de rotas
		      	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		      	DocumentBuilder builder = factory.newDocumentBuilder();
		      	Document doc = builder.parse("map/map.rou.xml");
		      	NodeList nList = doc.getElementsByTagName("vehicle");
		      	for (int i = 0; i < nList.getLength(); i++) {
		      		Node nNode = nList.item(i);
		      		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		      			Element elem = (Element) nNode;
		      			Node node = elem.getElementsByTagName("route").item(0);
		      			Element edges = (Element) node;
		      			rotas.add(new Route("Rota"+i, edges.getAttribute("edges")));
		      		}
		      	}
          
		      } catch (Exception e) {
		      	e.printStackTrace();
		      }
	      }
        
        // metodos array
        public void getRotas() {
          System.out.println("implementar");
        }
        
        public void rotasExecucao(Route rota) {
                this.rotas.remove(rota);
                this.rotasExecucao.add(rota);
        }
        
        
        public void rotasExecutadas(Route rota) {
                this.rotasExecucao.remove(rota);
                this.rotasExecutadas.add(rota);
        }

        @Override
        public void run() {
        // Servidor para cars e clients
        //Os servidores devem ter portas diferentes
            new Servidor(5000,new ServidorHandler() { 
            @Override
            public String handle(String msg) {
                //Tratar mensagens aqui. If (mensagem.equalsIgnoreCase(acao) return respostaEmJson

                if (msg.equalsIgnoreCase("rota")){
                    Route novaRotas = rotas.get(0);
                    rotasExecucao(novaRotas);
                    return rotas.remove(0).toJSON();
                };
                if(msg.equals("rota finalizada")){
                    Route rotaFinalizada = rotasExecucao.get(0);
                    rotasExecutadas(rotaFinalizada);
                }

                return "MENSAGEM INVÁLIDA";    
                }

            }).start();
                
    
        }
        
        // Criar relatorio

}
