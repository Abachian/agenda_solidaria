package ar.com.vwa.extranet.services.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.PasswordGenerator;
import org.passay.PasswordValidator;
import org.passay.Rule;
import org.passay.RuleResult;
import org.passay.WhitespaceRule;
import org.springframework.stereotype.Component;

@Component
public class PasswordRules {
	
	private List<CharacterRule> rules;
	
	private PasswordValidator validator;
	private PasswordGenerator generator;
	
	public PasswordRules() {
		this.rules = Arrays.asList(
	            // Al menos un dígito (0-9)
	            new CharacterRule(EnglishCharacterData.Digit, 1),
	            // Al menos una letra minúscula (a-z)
	            new CharacterRule(EnglishCharacterData.LowerCase, 1),
	            // Al menos una letra mayúscula (A-Z)
	            new CharacterRule(EnglishCharacterData.UpperCase, 1),
	            // Al menos uno de los siguientes símbolos: * - + ? _ $ < > = ! % { }
	            new CharacterRule(new org.passay.CharacterData() {
	                @Override
	                public String getErrorCode() {
	                    return "INSUFFICIENT_SPECIAL_CHARS";
	                }

	                @Override
	                public String getCharacters() {
	                    return "*-+?_$<=>!%{}";
	                }
	            }, 1)
	        );
		
		List<Rule> rules = new ArrayList<>();
		rules.add(new LengthRule(16, 30));
		rules.addAll(this.rules);
		rules.add(new WhitespaceRule());
		
		validator = new PasswordValidator(rules);
		
		generator = new PasswordGenerator();
	}

    public boolean isValidPassword(String password) {
        RuleResult result = validator.validate(new PasswordData(password));
        return result.isValid();
    }
    
    public String generatePassword() {
    	return this.generator.generatePassword(20, this.rules);
    }

}
