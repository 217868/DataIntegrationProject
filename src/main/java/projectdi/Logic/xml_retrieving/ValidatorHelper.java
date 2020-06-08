package projectdi.Logic.xml_retrieving;

import helpers.Const;
import projectdi.Logic.exceptions.ValidationFailedException;
import projectdi.Logic.exceptions.XMLNotFoundException;
import projectdi.Logic.imported.JDOMFunctions_Validation;


import java.io.IOException;

public class ValidatorHelper {

    public boolean dtdValidation() throws IOException, ValidationFailedException{
        if(JDOMFunctions_Validation.validateDTD(Const.XML_FILE_NAME.getValue()) == null) return false;
        return true;
    }

    public boolean xsdValidation() throws ValidationFailedException {
        if(JDOMFunctions_Validation.validateXSD(Const.XML_FILE_NAME.getValue()) == null) return false;
        return true;
    }

    public boolean validate() throws IOException, ValidationFailedException{
        return xsdValidation() && dtdValidation();
    }
}
