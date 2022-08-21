package io.github.jamalam360.colossal.cakes.registry;

import io.github.jamalam360.colossal.cakes.ColossalCakesInit;
import io.github.jamalam360.jamlib.registry.annotation.ContentRegistry;
import net.minecraft.sound.SoundEvent;

/**
 * @author Jamalam
 */

@ContentRegistry(ColossalCakesInit.MOD_ID)
public class ColossalCakesSounds {
    public static final SoundEvent ITEM_ROLLING_PIN_BONK = new SoundEvent(ColossalCakesInit.idOf("item_rolling_pin_bonk"));
    public static final SoundEvent ITEM_ROLLING_PIN_BONK_SWEEP = new SoundEvent(ColossalCakesInit.idOf("item_rolling_pin_bonk_sweep"));
    public static final SoundEvent ITEM_ROLLING_PIN_USE = new SoundEvent(ColossalCakesInit.idOf("item_rolling_pin_use"));
    public static final SoundEvent ITEM_WHISK_USE = new SoundEvent(ColossalCakesInit.idOf("item_whisk_use"));
}
