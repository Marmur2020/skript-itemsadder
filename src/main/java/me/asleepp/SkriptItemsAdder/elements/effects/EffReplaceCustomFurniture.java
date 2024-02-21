package me.asleepp.SkriptItemsAdder.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import dev.lone.itemsadder.api.CustomFurniture;
import org.bukkit.Location;

import javax.annotation.Nullable;

@Name("Replace Custom Furniture")
@Description({"Replace a custom furniture at a location."})
@Examples({"replace custom furniture \"comfy_chair\" at player's location"})
@Since("1.3")
public class EffReplaceCustomFurniture extends Effect {

    private Expression<String> furnitureId;
    private Expression<Location> location;

    static {
        Skript.registerEffect(EffReplaceCustomFurniture.class, "(replace) (custom|ia|itemsadder) furniture %string% at %location%");
    }

    @Override
    protected void execute(Event e) {
        String id = furnitureId.getSingle(e);
        Location loc = location.getSingle(e);

        if (id != null && loc != null) {
            // Get the existing furniture at the location
            CustomFurniture existingFurniture = CustomFurniture.byAlreadySpawned(loc.getBlock());

            // If there is existing furniture, remove it
            if (existingFurniture != null) {
                existingFurniture.remove(false);
            }

            // Spawn the new furniture
            CustomFurniture.spawn(id, loc.getBlock());
        }
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "(replace) (custom|ia|itemsadder) furniture " + furnitureId.toString(e, debug) + " at " + location.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        furnitureId = (Expression<String>) exprs[0];
        location = (Expression<Location>) exprs[1];
        return true;
    }
}
