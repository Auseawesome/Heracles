package earth.terrarium.heracles.forge;

import earth.terrarium.heracles.client.HeraclesClient;
import earth.terrarium.heracles.client.handlers.DisplayConfig;
import earth.terrarium.heracles.client.handlers.QuestTutorial;
import earth.terrarium.heracles.client.screens.pinned.PinnedQuestDisplay;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.loading.FMLPaths;

import static earth.terrarium.heracles.Heracles.MOD_ID;

@Mod(value = MOD_ID, dist = Dist.CLIENT)
public class HeraclesForgeClient {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        DisplayConfig.load(FMLPaths.GAMEDIR.get());
        QuestTutorial.load(FMLPaths.CONFIGDIR.get());
        event.enqueueWork(HeraclesClient::init);
    }

    @SubscribeEvent
    public static void onRegisterKeyBindings(RegisterKeyMappingsEvent event) {
        event.register(HeraclesClient.OPEN_QUESTS);
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent event) {
        //if (event.phase.equals(ClientTickEvent.Phase.START)) {
            HeraclesClient.clientTick();
        //}
    }

    @SubscribeEvent
    public static void onMouseClickedPreScreen(ScreenEvent.MouseButtonPressed.Pre event) {
        if (PinnedQuestDisplay.click(event.getMouseX(), event.getMouseY())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onClientReloadListeners(RegisterClientReloadListenersEvent event) {
        HeraclesClient.initReloadListeners((id, listener) -> event.registerReloadListener(listener));
    }
}
