package org.gluster.mobile.rsdlCrawler;

import org.gluster.mobile.rsdlCrawler.models.RestApiRsdlModel;

import java.util.Arrays;

/**
 * Created by ababu on 12/29/13.
 */
public class RestApiDescriptionEncoder {
    RestApiRsdlModel model;

    public RestApiDescriptionEncoder(RestApiRsdlModel model) {
        this.model = model;
    }

    public String encodeDescription() {
        StringBuilder encodedDescription = new StringBuilder();
        String[] words = (model.getDescription()).split(" ");
        DescriptionTranslatorCumUIMapper.GlusterVerbs[] glusterVerbs = DescriptionTranslatorCumUIMapper.GlusterVerbs.values();
        DescriptionTranslatorCumUIMapper.GlusterEntityMap[] glusterEntityMap = DescriptionTranslatorCumUIMapper.GlusterEntityMap.values();
        for(int i = 0; i < words.length; i++) {
            for(DescriptionTranslatorCumUIMapper.GlusterVerbs verb : glusterVerbs) {
                if(verb.toString().equalsIgnoreCase(words[i])) {
                    encodedDescription.append(verb.getVerbWeight());
                }
            }
            for(DescriptionTranslatorCumUIMapper.GlusterEntityMap entity : DescriptionTranslatorCumUIMapper.GlusterEntityMap.values()) {
                if(entity.toString().equalsIgnoreCase(words[i])) {
                    encodedDescription.append(entity.getEntityWeight());
                }
            }
        }
        char[] description = encodedDescription.toString().toCharArray();
        Arrays.sort(description);
        return (new String(description));
    }
}
