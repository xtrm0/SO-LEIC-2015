package edt.core;

import java.util.List;
import java.util.Stack;
import java.util.ArrayList;
import java.util.Iterator;

public class Section extends Node {
	private String title;
	private List<Paragraph> subParagraphs;
	private List<Section> subSections;

	public Section(Node parent, IdentificationFactory factory) {
		super(parent, factory);
		title = "";
		subParagraphs = new ArrayList<Paragraph>(); // TODO: isto tem delete
													// O(n). Podemos fazer uma
													// bst com as 3 operacoes
													// necessarias O(logn)
		subSections = new ArrayList<Section>(); // TODO: isto tem delete O(n).
												// Podemos fazer uma bst com as
												// 3 operacoes necessarias
												// O(logn)
	}

	@Override
	protected int calcLength() {
		int ans = title.length();
		for (int i=0; i<subSections.size(); i++)
			ans += subSections.get(i).getLength();
		for (int i=0; i<subParagraphs.size(); i++)
			ans += subParagraphs.get(i).getLength();
		return ans;
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
		if (title == null)
			title = "";
		this.title = title;
		updateLength();
	}

	public void insertSection(String title, int n) {
		Section x = new Section(this, this.factory);
		subSections.add(n, x);
		x.setTitle(title);
	}

	public void insertParagraph(String text, int n) {
		Paragraph x = new Paragraph(this, this.factory);
		subParagraphs.add(n, x);
		x.setText(text);
	}

	public void removeParagraph(int n) {
		subParagraphs.get(n).delete();
		subParagraphs.remove(n);
	}

	public void removeSection(int n) {
		subSections.get(n).delete();
		subSections.remove(n);
	}

	@Override
	public void delete() {
		super.delete();
		for (Paragraph p : subParagraphs) {
			p.delete();
		}

		Iterator<Section> it = getDirectIterator();
		while (it.hasNext()) {
			Section s = it.next();
			s.delete();
		}
	}

	public Integer getSectionsCount() {
		return subSections.size();
	}

	public Integer getParagraphsCount() {
		return subParagraphs.size();
	}

	public Iterator<Section> getPrefixIterator() {
		return new SectionPrefixRecursiveIterator(this);
	}

	public Iterator<Section> getDirectIterator() {
		return new SectionDirectChildIterator(this);
	}

	public void runAutoOperator(SectionOperator op) {
		SectionPrefixRecursiveIterator it = new SectionPrefixRecursiveIterator(this, op);
		while(it.hasNext()) it.next();
	}
}

class SectionPrefixRecursiveIterator implements Iterator<Section> {
	Section rootSection;
	Stack<Integer> idStack;
	SectionOperator op;
	boolean ended;

	public SectionPrefixRecursiveIterator(Section s) {
		rootSection = s;
		idStack = new Stack<Integer>();
		idStack.push(0);
		op=null;
	}

	public SectionPrefixRecursiveIterator(Section s, SectionOperator op) {
		this(s);
		this.op = op;
	}

	@Override
	public boolean hasNext() {
		return !ended;
	}

	@Override
	public Section next() { /*O op nao devia ser implementado no iterator, fica estranho :S*/
		if (!hasNext())
			return null;
		if (op!=null) op.onCall(rootSection);
		Section ret = rootSection;
		while (idStack.peek() == rootSection.getSectionsCount()) {
			if (op!=null) op.onRet(rootSection);
			rootSection = (Section) rootSection.getParent();
			idStack.pop();
			if (idStack.isEmpty()) {
				ended = true;
				return ret;
			}
		}
		rootSection = rootSection.getNthSection(idStack.peek());
		idStack.push(0);
		return ret;
	}
}

class SectionDirectChildIterator implements Iterator<Section> {
	Section rootSection;
	int n;

	public SectionDirectChildIterator(Section s) {
		rootSection = s;
		n = 0;
	}

	@Override
	public boolean hasNext() {
		return (n < rootSection.getSectionsCount());
	}

	@Override
	public Section next() {
		if (!hasNext())
			return null;
		n++;
		return rootSection.getNthSection(n - 1);
	}
}
