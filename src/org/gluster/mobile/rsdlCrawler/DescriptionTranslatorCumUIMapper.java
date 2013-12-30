package org.gluster.mobile.rsdlCrawler;

/**
 * Created by ababu on 12/29/13.
 */
public class DescriptionTranslatorCumUIMapper {
    public static enum GlusterEntityMap {
        Cluster(1),
        Host(2),
        Volume(3),
        Brick(4),
        Option(5);

        private long entityWeight;

        private GlusterEntityMap(long entityWeight) {
            this.entityWeight = entityWeight;
        }
        public long getEntityWeight() {
            return entityWeight;
        }
    };

    public  static enum GlusterVerbs {
        Get('a'),
        List('b'),
        Add('c'),
        Delete('d');

        private char verbWeight;

        private GlusterVerbs(char verbWeight) {
            this.verbWeight = verbWeight;
        }

        public char getVerbWeight() {
            return verbWeight;
        }
    }
}
