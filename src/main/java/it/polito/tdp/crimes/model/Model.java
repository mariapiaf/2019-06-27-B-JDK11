package it.polito.tdp.crimes.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.crimes.db.EventsDao;

public class Model {
	
	private EventsDao dao;
	private Graph<String, DefaultWeightedEdge> grafo;
	private List<String> soluzioneMigliore;
	
	public Model() {
		dao = new EventsDao();
	}
	
	public void creaGrafo(String categoria, Integer mese) {
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		// aggiungo vertici
		Graphs.addAllVertices(this.grafo, dao.getVertici(categoria, mese));
		
		// aggiungo archi
		for(Arco a: this.dao.getArchi(categoria, mese)) {
			if(grafo.containsVertex(a.getId1()) && grafo.containsVertex(a.getId2())) {
				if(grafo.getEdge(a.getId1(), a.getId2())== null && grafo.getEdge(a.getId2(), a.getId1())== null
						&& !a.getId1().equals(a.getId2()))
					Graphs.addEdge(this.grafo, a.getId1(), a.getId2(), a.getPeso());
			}
		}
		
	}
	
	public List<Arco> getArchiSupMedia() {
		List<Arco> result = new ArrayList<>();
		double pesoMedio = 0.0;
		double peso = 0.0;
		for(DefaultWeightedEdge edge: grafo.edgeSet()) {
			peso += grafo.getEdgeWeight(edge);
		}
		pesoMedio = peso / this.nArchi();
		
		for(DefaultWeightedEdge edge: grafo.edgeSet()) {
			if(grafo.getEdgeWeight(edge) > pesoMedio) {
				result.add(new Arco(grafo.getEdgeSource(edge), grafo.getEdgeTarget(edge), (int) grafo.getEdgeWeight(edge)));
			}
		}
		return result;
	}
	
	public List<String> trovaPercorso(String partenza, String arrivo){
		soluzioneMigliore = new ArrayList<>();
		
		List<String> parziale = new ArrayList<>();
		parziale.add(partenza);
		
		cerca(parziale);
		return soluzioneMigliore;
	}
	
	public void cerca(List<String> parziale) {
		
		if(parziale.size()> soluzioneMigliore.size()) {
			soluzioneMigliore = new ArrayList<>(parziale);
		}
		String ultimo = parziale.get(parziale.size()-1);
		
		List<String> vicini = Graphs.neighborListOf(this.grafo, ultimo);
		for(String vicino: vicini) {
			if(!parziale.contains(vicino)) {
				parziale.add(vicino);
				cerca(parziale);
				parziale.remove(vicino);
			}
		}
		
		
	}
	
	public List<String> getCategorie(){
		return this.dao.getCategorie();
	}
	
	public List<Integer> getMesi(){
		return this.dao.getMesi();
	}
	
	public int nVertici() {
		return grafo.vertexSet().size();
	}
	
	public int nArchi() {
		return grafo.edgeSet().size();
	}
}
