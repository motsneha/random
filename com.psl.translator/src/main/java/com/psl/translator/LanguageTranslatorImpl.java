package com.psl.translator;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.appiancorp.exceptions.InsufficientPrivilegesException;
import com.appiancorp.exceptions.ObjectNotFoundException;
import com.appiancorp.services.ServiceContext;
import com.appiancorp.suiteapi.expression.annotations.Function;
import com.appiancorp.suiteapi.expression.annotations.Parameter;
import com.appiancorp.suiteapi.security.external.SecureCredentialsStore;
import com.ibm.watson.developer_cloud.language_translator.v2.model.Translation;
import com.ibm.watson.developer_cloud.language_translator.v2.model.TranslationResult;
import com.ibm.watson.developer_cloud.language_translator.v2.LanguageTranslator;
import com.ibm.watson.developer_cloud.language_translator.v2.model.Language;

@com.appiancorp.suiteapi.expression.annotations.AppianScriptingFunctionsCategory
public class LanguageTranslatorImpl {

	private static final Logger LOG = Logger.getLogger(LanguageTranslatorImpl.class);

	@Function
	public String[] translateTextToGivenLanguage(ServiceContext sc, SecureCredentialsStore secureCredentialsStore,
			@Parameter String scsKey, @Parameter String[] textToTranslate, @Parameter String targetLanguage,
			@Parameter String sourceLanguage) {

		Map<String, String> userSecuredValues;
		try {
			userSecuredValues = secureCredentialsStore.getSystemSecuredValues(scsKey);

			String username = userSecuredValues.get("username");
			String password = userSecuredValues.get("password");

			LanguageTranslator service = new LanguageTranslator();
			service.setUsernameAndPassword(username, password);

			List<String> textList = Arrays.asList(textToTranslate);
			TranslationResult result = service.translate(textList, Language.valueOf(sourceLanguage),
					Language.valueOf(targetLanguage)).execute();

			List<Translation> translations = result.getTranslations();
			if (translations != null && translations.size() > 0) {
				String[] translatedtext = new String[translations.size()];

				for (int i = 0; i < translations.size(); i++) {
					translatedtext[i] = translations.get(i).getTranslation();
				}
				return translatedtext;
			}
			return null;
		} catch (InsufficientPrivilegesException | ObjectNotFoundException | Exception e) {
			LOG.error("Soemthing went wrong", e);
			return null;
		}
	}
}
