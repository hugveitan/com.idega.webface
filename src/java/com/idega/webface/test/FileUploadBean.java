/*
 * $Id: FileUploadBean.java,v 1.2 2004/06/18 14:55:23 anders Exp $
 *
 * Copyright (C) 2004 Idega. All Rights Reserved.
 *
 * This software is the proprietary information of Idega.
 * Use is subject to license terms.
 *
 */
package com.idega.webface.test;

import java.io.IOException;

//import net.sourceforge.myfaces.custom.fileupload.UploadedFile;

import com.idega.webface.WFUtil;


/**
 * Bean holding uploaded file data. 
 * <p>
 * Last modified: $Date: 2004/06/18 14:55:23 $ by $Author: anders $
 *
 * @author Anders Lindman
 * @version $Revision: 1.2 $
 */
public class FileUploadBean {
/*	
    private UploadedFile _upFile;
    private String _name = "";

    public UploadedFile getUpFile() {
        return _upFile;
    }

    public void setUpFile(UploadedFile upFile) {
        _upFile = upFile;
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        _name = name;
    }
*/
    public void upload() throws IOException {
//		WFUtil.invoke(ArticleBlock.ARTICLE_ITEM_BEAN_ID, "addImage", _upFile.getBytes(), _upFile.getContentType());
    }
}
