package io.github.jpmillz.jpChatCustomization.ranks;

import java.util.List;

public record Rank(String name, String identifier, List<String> playerUUIDs) {

    @Override
    public boolean equals(Object obj) {
        if (this == obj){
            return true;
        }
        if (!(obj instanceof Rank)){
            return false;
        }
        Rank cast = (Rank) obj;
        if (this.name.equals(cast.name())){
            return true;
        }
        return false;
    }
}

