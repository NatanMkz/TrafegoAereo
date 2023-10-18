/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trafegoaereo.agents;

import jade.core.Agent;
import jade.core.AID;

/**
 *
 * @author Natan
 */
public class Aeronave extends Agent {
    int passageiros = 0;
    double combustivel = 0;
    String origem ="";
    String rotaAtual = "";
    boolean solicitaPouso = false;
    boolean solicitaDecolagem = false;
    //double porcentagemTotalRota = 0;
    //string endereçoAgenteOrigem = "";
    String destino = "";
   
    
    public Aeronave(){}

    public Aeronave(int passageiros, double combustivel, String origem, String destino, String rotaAtual, boolean solicitaPouso, boolean solicitaDecolagem)
    {
        this.passageiros = passageiros;
        this.combustivel = combustivel;
        this.origem = origem;
        this.destino = destino;
        this.rotaAtual = rotaAtual;
        this.solicitaPouso = solicitaPouso;
        this.solicitaDecolagem = solicitaDecolagem;
    }
    
    
    public double ChecaQuantidadeCombustivel(double quantidadeGasta){
        double combustivel = this.combustivel;
        try
        {
            if(this.solicitaPouso == false && this.destino != ""){
                this.combustivel = combustivel - quantidadeGasta;
            }
        } 
        catch(Exception ex){
        throw  ex;
        }
        
        return this.combustivel;
    }
    
    public static Aeronave ConstroiAeronave()
    {        
        return new Aeronave(50, 100, "Navegantes SC-Brasil", "Jerusalem Js-IR", "Navegantes SC-Brasil", false, true);
    }
    
    public boolean LiberaPouso(String rotaAtual){
        boolean pouso = false;
        try
        {
            this.rotaAtual = rotaAtual;
            //usei pra atualizar a propriedade da rota e declarar que o pouso foi solicitado
            if(this.rotaAtual.equals(this.destino))
            {
                this.solicitaPouso = true;                
                //da pra colocar uns comandos pra ele interagir com o controle aereo e o aeroporto
                //por enquanto eu vou deixar o pouso como true para ter um retorno mas isso pode ser uma request para os agentes da torre e do aeroporto
                pouso = true;
            }
        } 
        catch(Exception ex){
        throw  ex;
        }
        
        return pouso;
    }
    
    public void Setup()
    {
        //to seguindo o exemplo do livro no manual
        System.out.println("Aeronave: " + "Solicitando decolagem para torre de controle cambio! ");
    }
    
}
