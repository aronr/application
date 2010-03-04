package org.collectionspace.chain.csp.schema;

import java.util.ArrayList;
import java.util.List;

import org.collectionspace.chain.csp.config.ReadOnlySection;

// XXX only one level of repetition at the moment. Should only be a matter of type furtling.
public class Repeat implements FieldSet, FieldParent {
	private String id,selector;
	private FieldParent parent;
	private List<FieldSet> children=new ArrayList<FieldSet>();
	private boolean xxx_services_no_repeat=false;
	
	/* Services */
	private String services_tag;
	
	public Repeat(Record record,ReadOnlySection section) {
		this.parent=record;
		id=(String)section.getValue("/@id");
		selector=(String)section.getValue("/selector");
		services_tag=Util.getStringOrDefault(section,"/services-tag",id);
		xxx_services_no_repeat=Util.getBooleanOrDefault(section,"/@xxx-services-no-repeat",false);
	}

	public String getID() { return id; }

	void addChild(FieldSet f) { children.add(f); }
	public FieldSet[] getChildren() { return children.toArray(new FieldSet[0]); }

	public Record getRecord() { return parent.getRecord(); }
	public String getSelector() { return selector; }
	public String getServicesTag() { return services_tag; }
	public boolean getXxxServicesNoRepeat() { return xxx_services_no_repeat; }
	
	public String[] getIDPath() { 
		if(parent instanceof Repeat) {
			String[] pre=((Repeat)parent).getIDPath();
			String[] out=new String[pre.length+1];
			System.arraycopy(pre,0,out,0,pre.length);
			out[pre.length]=id;
			return out;
		} else {
			return new String[]{id};
		}
	}
}
