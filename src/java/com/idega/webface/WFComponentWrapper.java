/*
 * $Id: WFComponentWrapper.java,v 1.1 2005/01/10 13:52:19 gummi Exp $
 * Created on 9.1.2005
 *
 * Copyright (C) 2005 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.webface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.StateHolder;
import javax.faces.component.UIComponent;
import javax.faces.component.ValueHolder;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.el.MethodBinding;
import javax.faces.el.ValueBinding;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.FacesEvent;
import javax.faces.event.FacesListener;
import javax.faces.event.ValueChangeListener;
import javax.faces.render.Renderer;
import javax.faces.validator.Validator;


/**
 * 
 *  Last modified: $Date: 2005/01/10 13:52:19 $ by $Author: gummi $
 * 
 * @author <a href="mailto:gummi@idega.com">Gudmundur Agust Saemundsson</a>
 * @version $Revision: 1.1 $
 */
public class WFComponentWrapper extends UIComponent implements EditableValueHolder {

	private static final String FACETKEY_WRAPPED_COMPONENT = "wf_wrapped_component";
	private Map _facetMap=null;
//	private List tmpChildList = null;
	private boolean isRestored = false;
	
	/**
	 * 
	 */
	public WFComponentWrapper() {
		super();
//		tmpChildList = new ChildrenListWrapper();
		isRestored = true;
	}
	
	public WFComponentWrapper(UIComponent component){
		setComponent(component);
	}
	
	protected void setComponent(UIComponent component){
		if(_facetMap==null){
			_facetMap = new FacetMapWrapper(component);
		}
	}
	
	public UIComponent getComponent(){
		return getFacet(FACETKEY_WRAPPED_COMPONENT);
	}

	public ValueHolder getValueHolder(){
		UIComponent c = getComponent();
		if(c instanceof ValueHolder){
			return (ValueHolder)c;
		}
		return null;
	}
	
	public StateHolder getStateHolder(){
		UIComponent c = getComponent();
		if(c instanceof StateHolder){
			return (StateHolder)c;
		}
		return null;
	}
	
	public EditableValueHolder getEditableValueHolder() {
		UIComponent c = getComponent();
		if(c instanceof EditableValueHolder){
			return (EditableValueHolder)c;
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see javax.faces.component.UIComponent#getFamily()
	 */
	public String getFamily() {
//		return WFConstants.FAMILY_WEBFACE;
		return getComponent().getFamily();
	}
	
	
	/* (non-Javadoc)
	 * @see javax.faces.component.UIComponent#getFacets()
	 */
	public Map getFacets() {
		if(_facetMap == null){
			_facetMap = new FacetMapWrapper(null);
		}
		return _facetMap;
	}

	/* (non-Javadoc)
	 * @see javax.faces.component.UIComponent#getFacet(java.lang.String)
	 */
	public UIComponent getFacet(String name) {
		Map m = getFacets();
		if(m!=null){
			return (UIComponent)m.get(name);
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see javax.faces.component.UIComponent#getChildren()
	 */
	public List getChildren() {
		UIComponent c = getComponent();
		if(c!=null){
			return c.getChildren();
		}
//		return tmpChildList;
//		if(isRestored){
		return new ArrayList(); //Dummy list to avoid nullpointer when resoring
//		}
	}

	/* (non-Javadoc)
	 * @see javax.faces.component.UIComponent#getChildCount()
	 */
	public int getChildCount() {
		return getChildren().size();
	}
	
	public class ChildrenListWrapper extends ArrayList {
		
		public Object set(int index, Object value)
	    {
	        checkValue(value);
	        UIComponent child = (UIComponent) super.set(index, value);
	        if (child != null) child.setParent(null);
	        return child;
	    }
		
		public boolean add(Object obj){
			checkValue(obj);
			return super.add(obj);
		}

	    public void add(int index, Object value)
	    {
	        checkValue(value);
	        super.add(index, value);
	    }
		
		private void checkValue(Object value)
	    {
	        if (value == null) throw new NullPointerException("value");
	        if (!(value instanceof UIComponent)) throw new ClassCastException("value is not a UIComponent");
	    }
		
	}
	
	public class FacetMapWrapper implements Map {
		
		private Map componentFacetMap=null;
		private Map wrapperMap;
		private Map tmpComponentFacetMap;
		
		public FacetMapWrapper(UIComponent wrappedComponent){
			this.wrapperMap = new HashMap();
			if(wrappedComponent != null){
				this.componentFacetMap = wrappedComponent.getFacets();
				put(FACETKEY_WRAPPED_COMPONENT,wrappedComponent);
			} else {
				this.tmpComponentFacetMap = new HashMap();
			}
			
		}
		
		public boolean useWrapperMap(Object key){
			return FACETKEY_WRAPPED_COMPONENT.equals(key);
		}
		
		protected Map getComponentMap(){
			if(componentFacetMap == null){
				return tmpComponentFacetMap;
			} else {
				return componentFacetMap;
			}
		}
		
		private void checkKey(Object key)
	    {
	        if (key == null) throw new NullPointerException("key");
	        if (!(key instanceof String)) throw new ClassCastException("key is not a String");
	    }

	    private void checkValue(Object value)
	    {
	        if (value == null) throw new NullPointerException("value");
	        if (!(value instanceof UIComponent)) throw new ClassCastException("value is not a UIComponent");
	    }
		

		/* (non-Javadoc)
		 * @see java.util.Map#size()
		 */
		public int size() {
			return getComponentMap().size()+wrapperMap.size();
		}

		/* (non-Javadoc)
		 * @see java.util.Map#isEmpty()
		 */
		public boolean isEmpty() {
			return getComponentMap().isEmpty() && wrapperMap.isEmpty();
		}

		/* (non-Javadoc)
		 * @see java.util.Map#containsKey(java.lang.Object)
		 */
		public boolean containsKey(Object key) {
			return getComponentMap().containsKey(key) || wrapperMap.containsKey(key);
		}

		/* (non-Javadoc)
		 * @see java.util.Map#containsValue(java.lang.Object)
		 */
		public boolean containsValue(Object value) {
			return getComponentMap().containsValue(value) || wrapperMap.containsValue(value);
		}

		/* (non-Javadoc)
		 * @see java.util.Map#get(java.lang.Object)
		 */
		public Object get(Object key) {
			checkKey(key);
			if(useWrapperMap(key)){
				return wrapperMap.get(key);
			} else {
				return getComponentMap().get(key);
			}
		}

		/* (non-Javadoc)
		 * @see java.util.Map#put(java.lang.Object, java.lang.Object)
		 */
		public Object put(Object key, Object value) {
			if(useWrapperMap(key)){
				checkKey(key);
				checkValue(value);
				if(FACETKEY_WRAPPED_COMPONENT.equals(key)){
					componentFacetMap = ((UIComponent)value).getFacets();
					if(tmpComponentFacetMap!=null){
						componentFacetMap.putAll(tmpComponentFacetMap);
						tmpComponentFacetMap = null;
					}
//					if(tmpChildList != null){
//						((UIComponent)value).getChildren().addAll(tmpChildList);
//						tmpChildList = null;
//					}
				}
				return wrapperMap.put(key,value);
			} else {
				return getComponentMap().put(key,value);
			}
		}

		/* (non-Javadoc)
		 * @see java.util.Map#remove(java.lang.Object)
		 */
		public Object remove(Object key) {
			if(useWrapperMap(key)){
				return wrapperMap.remove(key);
			} else {
				return getComponentMap().remove(key);
			}
		}

		/* (non-Javadoc)
		 * @see java.util.Map#putAll(java.util.Map)
		 */
		public void putAll(Map t) {
			getComponentMap().putAll(t);
		}

		/* (non-Javadoc)
		 * @see java.util.Map#clear()
		 */
		public void clear() {
			getComponentMap().clear();
		}

		/* (non-Javadoc)
		 * @see java.util.Map#keySet()
		 */
		public Set keySet() {
			TreeSet set = new TreeSet();
			set.addAll(wrapperMap.keySet());
			set.addAll(getComponentMap().keySet());
			return set;
		}

		/* (non-Javadoc)
		 * @see java.util.Map#values()
		 */
		public Collection values() {
			ArrayList l = new ArrayList();
			l.addAll(wrapperMap.values());
			l.addAll(getComponentMap().values());
			return l;
		}

		/* (non-Javadoc)
		 * @see java.util.Map#entrySet()
		 */
		public Set entrySet() {
			TreeSet set = new TreeSet();
			set.addAll(wrapperMap.entrySet());
			set.addAll(getComponentMap().entrySet());
			return set;
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//-------- ValueHolder methods ----------//

	/* (non-Javadoc)
	 * @see javax.faces.component.ValueHolder#getLocalValue()
	 */
	public Object getLocalValue() {
		ValueHolder v = getValueHolder();
		if(v!=null){
			return v.getLocalValue();
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.faces.component.ValueHolder#getValue()
	 */
	public Object getValue() {
		ValueHolder v = getValueHolder();
		if(v!=null){
			return v.getValue();
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.faces.component.ValueHolder#setValue(java.lang.Object)
	 */
	public void setValue(Object value) {
		ValueHolder v = getValueHolder();
		if(v!=null){
			v.setValue(value);
		}
	}

	/* (non-Javadoc)
	 * @see javax.faces.component.ValueHolder#getConverter()
	 */
	public Converter getConverter() {
		ValueHolder v = getValueHolder();
		if(v!=null){
			return v.getConverter();
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.faces.component.ValueHolder#setConverter(javax.faces.convert.Converter)
	 */
	public void setConverter(Converter converter) {
		ValueHolder v = getValueHolder();
		if(v!=null){
			v.setConverter(converter);
		}
	}

	
	//-------- EditableValueHolder methods ----------//
	
	/* (non-Javadoc)
	 * @see javax.faces.component.EditableValueHolder#getSubmittedValue()
	 */
	public Object getSubmittedValue() {
		EditableValueHolder h = getEditableValueHolder();
		if(h!=null){
			return h.getSubmittedValue();
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.faces.component.EditableValueHolder#setSubmittedValue(java.lang.Object)
	 */
	public void setSubmittedValue(Object submittedValue) {
		EditableValueHolder h = getEditableValueHolder();
		if(h!=null){
			h.setSubmittedValue(submittedValue);
		}
	}

	/* (non-Javadoc)
	 * @see javax.faces.component.EditableValueHolder#isLocalValueSet()
	 */
	public boolean isLocalValueSet() {
		EditableValueHolder h = getEditableValueHolder();
		if(h!=null){
			return h.isLocalValueSet();
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.faces.component.EditableValueHolder#setLocalValueSet(boolean)
	 */
	public void setLocalValueSet(boolean localValueSet) {
		EditableValueHolder h = getEditableValueHolder();
		if(h!=null){
			h.setLocalValueSet(localValueSet);
		}
	}

	/* (non-Javadoc)
	 * @see javax.faces.component.EditableValueHolder#isValid()
	 */
	public boolean isValid() {
		EditableValueHolder h = getEditableValueHolder();
		if(h!=null){
			return h.isValid();
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see javax.faces.component.EditableValueHolder#setValid(boolean)
	 */
	public void setValid(boolean valid) {
		EditableValueHolder h = getEditableValueHolder();
		if(h!=null){
			h.setValid(valid);
		}
	}

	/* (non-Javadoc)
	 * @see javax.faces.component.EditableValueHolder#isRequired()
	 */
	public boolean isRequired() {
		EditableValueHolder h = getEditableValueHolder();
		if(h!=null){
			return h.isRequired();
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.faces.component.EditableValueHolder#setRequired(boolean)
	 */
	public void setRequired(boolean required) {
		EditableValueHolder h = getEditableValueHolder();
		if(h!=null){
			h.setRequired(required);
		}
	}

	/* (non-Javadoc)
	 * @see javax.faces.component.EditableValueHolder#isImmediate()
	 */
	public boolean isImmediate() {
		EditableValueHolder h = getEditableValueHolder();
		if(h!=null){
			return h.isImmediate();
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.faces.component.EditableValueHolder#setImmediate(boolean)
	 */
	public void setImmediate(boolean immediate) {
		EditableValueHolder h = getEditableValueHolder();
		if(h!=null){
			h.setImmediate(immediate);
		}
	}

	/* (non-Javadoc)
	 * @see javax.faces.component.EditableValueHolder#getValidator()
	 */
	public MethodBinding getValidator() {
		EditableValueHolder h = getEditableValueHolder();
		if(h!=null){
			return h.getValidator();
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.faces.component.EditableValueHolder#setValidator(javax.faces.el.MethodBinding)
	 */
	public void setValidator(MethodBinding validatorBinding) {
		EditableValueHolder h = getEditableValueHolder();
		if(h!=null){
			h.setValidator(validatorBinding);
		}
	}

	/* (non-Javadoc)
	 * @see javax.faces.component.EditableValueHolder#getValueChangeListener()
	 */
	public MethodBinding getValueChangeListener() {
		EditableValueHolder h = getEditableValueHolder();
		if(h!=null){
			return h.getValueChangeListener();
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.faces.component.EditableValueHolder#setValueChangeListener(javax.faces.el.MethodBinding)
	 */
	public void setValueChangeListener(MethodBinding valueChangeMethod) {
		EditableValueHolder h = getEditableValueHolder();
		if(h!=null){
			h.setValueChangeListener(valueChangeMethod);
		}
	}

	/* (non-Javadoc)
	 * @see javax.faces.component.EditableValueHolder#addValidator(javax.faces.validator.Validator)
	 */
	public void addValidator(Validator validator) {
		EditableValueHolder h = getEditableValueHolder();
		if(h!=null){
			h.addValidator(validator);
		}
	}

	/* (non-Javadoc)
	 * @see javax.faces.component.EditableValueHolder#getValidators()
	 */
	public Validator[] getValidators() {
		EditableValueHolder h = getEditableValueHolder();
		if(h!=null){
			return h.getValidators();
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.faces.component.EditableValueHolder#removeValidator(javax.faces.validator.Validator)
	 */
	public void removeValidator(Validator validator) {
		EditableValueHolder h = getEditableValueHolder();
		if(h!=null){
			h.removeValidator(validator);
		}
	}

	/* (non-Javadoc)
	 * @see javax.faces.component.EditableValueHolder#addValueChangeListener(javax.faces.event.ValueChangeListener)
	 */
	public void addValueChangeListener(ValueChangeListener listener) {
		EditableValueHolder h = getEditableValueHolder();
		if(h!=null){
			h.addValueChangeListener(listener);
		}
	}

	/* (non-Javadoc)
	 * @see javax.faces.component.EditableValueHolder#getValueChangeListeners()
	 */
	public ValueChangeListener[] getValueChangeListeners() {
		EditableValueHolder h = getEditableValueHolder();
		if(h!=null){
			return h.getValueChangeListeners();
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.faces.component.EditableValueHolder#removeValueChangeListener(javax.faces.event.ValueChangeListener)
	 */
	public void removeValueChangeListener(ValueChangeListener listener) {
		EditableValueHolder h = getEditableValueHolder();
		if(h!=null){
			h.removeValueChangeListener(listener);
		}
	}
	
	
	
	//-------- ValueHolder methods ----------//
	
	
	/* (non-Javadoc)
	 * @see javax.faces.component.UIComponent#encodeChildren(javax.faces.context.FacesContext)
	 */
	public void encodeChildren(FacesContext context) throws IOException{
		UIComponent c = getComponent();
		if(c!=null){
			c.encodeChildren(context);
		}
	}
	
	/* (non-Javadoc)
	 * @see javax.faces.component.UIComponent#encodeBegin(javax.faces.context.FacesContext)
	 */
	public void encodeBegin(FacesContext context) throws java.io.IOException {
		UIComponent c = getComponent();
		if(c!=null){
			c.encodeBegin(context);
		}
	}
	
	/* (non-Javadoc)
	 * @see javax.faces.component.UIComponent#encodeEnd(javax.faces.context.FacesContext)
	 */
	public void encodeEnd(FacesContext context) throws java.io.IOException{
		UIComponent c = getComponent();
		if(c!=null){
			c.encodeEnd(context);
		}
	}

	/* (non-Javadoc)
	 * @see javax.faces.component.UIComponent#getAttributes()
	 */
	public Map getAttributes() {
		UIComponent c = getComponent();
		if(c!=null){
			return c.getAttributes();
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.faces.component.UIComponent#getValueBinding(java.lang.String)
	 */
	public ValueBinding getValueBinding(String name) {
		UIComponent c = getComponent();
		if(c!=null){
			return c.getValueBinding(name);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.faces.component.UIComponent#setValueBinding(java.lang.String, javax.faces.el.ValueBinding)
	 */
	public void setValueBinding(String name, ValueBinding binding) {
		UIComponent c = getComponent();
		if(c!=null){
			c.setValueBinding(name,binding);
		}
	}

	/* (non-Javadoc)
	 * @see javax.faces.component.UIComponent#getClientId(javax.faces.context.FacesContext)
	 */
	public String getClientId(FacesContext context) {
		UIComponent c = getComponent();
		if(c!=null){
			return c.getClientId(context);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.faces.component.UIComponent#getId()
	 */
	public String getId() {
		UIComponent c = getComponent();
		if(c!=null){
			return c.getId();
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.faces.component.UIComponent#setId(java.lang.String)
	 */
	public void setId(String id) {
		UIComponent c = getComponent();
		if(c!=null){
			c.setId(id);
		}
	}

	/* (non-Javadoc)
	 * @see javax.faces.component.UIComponent#getParent()
	 */
	public UIComponent getParent() {
		UIComponent c = getComponent();
		if(c!=null){
			return c.getParent();
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.faces.component.UIComponent#setParent(javax.faces.component.UIComponent)
	 */
	public void setParent(UIComponent parent) {
		UIComponent c = getComponent();
		if(c!=null){
			c.setParent(parent);
		}
	}

	/* (non-Javadoc)
	 * @see javax.faces.component.UIComponent#isRendered()
	 */
	public boolean isRendered() {
		UIComponent c = getComponent();
		if(c!=null){
			return c.isRendered();
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.faces.component.UIComponent#setRendered(boolean)
	 */
	public void setRendered(boolean rendered) {
		UIComponent c = getComponent();
		if(c!=null){
			c.setRendered(rendered);
		}
	}

	/* (non-Javadoc)
	 * @see javax.faces.component.UIComponent#getRendererType()
	 */
	public String getRendererType() {
		UIComponent c = getComponent();
		if(c!=null){
			return c.getRendererType();
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.faces.component.UIComponent#setRendererType(java.lang.String)
	 */
	public void setRendererType(String rendererType) {
		UIComponent c = getComponent();
		if(c!=null){
			c.setRendererType(rendererType);
		}
	}

	/* (non-Javadoc)
	 * @see javax.faces.component.UIComponent#getRendersChildren()
	 */
	public boolean getRendersChildren() {
		UIComponent c = getComponent();
		if(c!=null){
			return c.getRendersChildren();
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see javax.faces.component.UIComponent#findComponent(java.lang.String)
	 */
	public UIComponent findComponent(String expr) {
		UIComponent c = getComponent();
		if(c!=null){
			return c.findComponent(expr);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.faces.component.UIComponent#getFacetsAndChildren()
	 */
	public Iterator getFacetsAndChildren() {
		UIComponent c = getComponent();
		if(c!=null){
			return c.getFacetsAndChildren();
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.faces.component.UIComponent#broadcast(javax.faces.event.FacesEvent)
	 */
	public void broadcast(FacesEvent event) throws AbortProcessingException {
		UIComponent c = getComponent();
		if(c!=null){
			c.broadcast(event);
		}
	}

	/* (non-Javadoc)
	 * @see javax.faces.component.UIComponent#decode(javax.faces.context.FacesContext)
	 */
	public void decode(FacesContext context) {
		UIComponent c = getComponent();
		if(c!=null){
			c.decode(context);
		}
	}

	/* (non-Javadoc)
	 * @see javax.faces.component.UIComponent#queueEvent(javax.faces.event.FacesEvent)
	 */
	public void queueEvent(FacesEvent event) {
		UIComponent c = getComponent();
		if(c!=null){
			c.queueEvent(event);
		}
	}

	/* (non-Javadoc)
	 * @see javax.faces.component.UIComponent#processRestoreState(javax.faces.context.FacesContext, java.lang.Object)
	 */
	public void processRestoreState(FacesContext context, Object state) {
		UIComponent c = getComponent();
		if(c!=null){
			c.processRestoreState(context,state);
		}
	}

	/* (non-Javadoc)
	 * @see javax.faces.component.UIComponent#processDecodes(javax.faces.context.FacesContext)
	 */
	public void processDecodes(FacesContext context) {
		UIComponent c = getComponent();
		if(c!=null){
			c.processDecodes(context);
		}
	}

	/* (non-Javadoc)
	 * @see javax.faces.component.UIComponent#processValidators(javax.faces.context.FacesContext)
	 */
	public void processValidators(FacesContext context) {
		UIComponent c = getComponent();
		if(c!=null){
			c.processValidators(context);
		}
	}

	/* (non-Javadoc)
	 * @see javax.faces.component.UIComponent#processUpdates(javax.faces.context.FacesContext)
	 */
	public void processUpdates(FacesContext context) {
		UIComponent c = getComponent();
		if(c!=null){
			c.processUpdates(context);
		}
	}

	/* (non-Javadoc)
	 * @see javax.faces.component.UIComponent#processSaveState(javax.faces.context.FacesContext)
	 */
	public Object processSaveState(FacesContext context) {
		UIComponent c = getComponent();
		if(c!=null){
			return c.processSaveState(context);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.faces.component.StateHolder#saveState(javax.faces.context.FacesContext)
	 */
	public Object saveState(FacesContext context) {
		StateHolder s = getStateHolder();
		if(s!=null){
			return s.saveState(context);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.faces.component.StateHolder#restoreState(javax.faces.context.FacesContext, java.lang.Object)
	 */
	public void restoreState(FacesContext context, Object state) {
		StateHolder s = getStateHolder();
		if(s!=null){
			s.restoreState(context,state);
		}
	}

	/* (non-Javadoc)
	 * @see javax.faces.component.StateHolder#isTransient()
	 */
	public boolean isTransient() {
		StateHolder s = getStateHolder();
		if(s!=null){
			return s.isTransient();
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.faces.component.StateHolder#setTransient(boolean)
	 */
	public void setTransient(boolean newTransientValue) {
		StateHolder s = getStateHolder();
		if(s!=null){
			s.setTransient(newTransientValue);
		}
	}
	
	
	//---------- UIComponent abstract protected methods -------------//
	

	/* (non-Javadoc)
	 * @see javax.faces.component.UIComponent#addFacesListener(javax.faces.event.FacesListener)
	 */
	protected void addFacesListener(FacesListener listener) {
		// TODO Auto-generated method stub
		System.err.println("[Warning]["+getClass().getName()+"][addFacesListener]: This abstract method in UIComponent this wrapper has not implemented and cannot easily do since it is protected");
	}

	/* (non-Javadoc)
	 * @see javax.faces.component.UIComponent#getFacesListeners(java.lang.Class)
	 */
	protected FacesListener[] getFacesListeners(Class clazz) {
		// TODO Auto-generated method stub
		System.err.println("[Warning]["+getClass().getName()+"][getFacesListeners]: This abstract method in UIComponent this wrapper has not implemented and cannot easily do since it is protected");
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.faces.component.UIComponent#removeFacesListener(javax.faces.event.FacesListener)
	 */
	protected void removeFacesListener(FacesListener listener) {
		// TODO Auto-generated method stub
		System.err.println("[Warning]["+getClass().getName()+"][removeFacesListener]: This abstract method in UIComponent this wrapper has not implemented and cannot easily do since it is protected");
	}
	
	/* (non-Javadoc)
	 * @see javax.faces.component.UIComponent#getFacesContext()
	 */
	protected FacesContext getFacesContext()
    {
        return FacesContext.getCurrentInstance();
    }

	/* (non-Javadoc)
	 * @see javax.faces.component.UIComponent#getRenderer(javax.faces.context.FacesContext)
	 */
	protected Renderer getRenderer(FacesContext context) {
		// TODO Auto-generated method stub
		System.err.println("[Warning]["+getClass().getName()+"][getRenderer]: This abstract method in UIComponent this wrapper has not implemented and cannot easily do since it is protected");
		return null;
	}
	
}