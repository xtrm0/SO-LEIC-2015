package edt.core;

import java.util.List;
import java.util.ArrayList;

public class Section extends Node{
	private String title;
	private List<Paragraph> subParagraphs; 
	private List<Section> subSections;
	public Section(Node parent, IdentificationFactory factory) {
		super(parent, factory);
		title = null;
		subParagraphs = new ArrayList<Paragraph>(); //TODO: isto tem delete O(n). Podemos fazer uma bst com as 3 operacoes necessarias O(logn)
		subSections = new ArrayList<Section>(); //TODO: isto tem delete O(n). Podemos fazer uma bst com as 3 operacoes necessarias O(logn)
	}

	@Override
	protected int calcLength() {
		//WARNING: TEMOS DE INCLUIR O LENGHT DOS FILHOS
		return 0;
	}
	
	public Paragraph getNthParagraph(int n) {
		if (subParagraphs.size() < n || n < 0)
			return subParagraphs.get(n);
		return null;
	}
	public Section getNthSection(int n) {
		if (subSections.size() < n || n < 0)
			return subSections.get(n);
		return null;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
		updateLength();
	}
	
	void insertSection(String title, int n) {
		if (subSections.size() < n || n < 0) {
			n = subSections.size();
		}
		Section x = new Section(this, this.factory);
		subSections.add(n, x);
		x.setTitle(title);
	}
	
	void recursiveCall(SectionRecursiveOperator c) {
		c.execute(this);
		for (Section sect : subSections) {
			sect.recursiveCall(c);
		}
		c.executePostCall(this);
	}
	
	public void deleteParagraph(int n) {
		subParagraphs.get(n).delete();
		subParagraphs.remove(n);
	}
	
	public void deleteSubsection(int n) {
		//TODO: recursive
		subSections.get(n).delete();
		subSections.remove(n);
	}
	
	@Override
	public void delete() {
		super.delete();
		//TODO:
	}
}


class SOListSections implements SectionRecursiveOperator {

	@Override
	public void execute(Section s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void executePostCall(Section s) {
		// TODO Auto-generated method stub
		
	}
	
}

class SOShowContent implements SectionRecursiveOperator {

	@Override
	public void execute(Section s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void executePostCall(Section s) {
		// TODO Auto-generated method stub
		
	}
	
}
