world.setBlock(posI.x, posI.y, posI.z, String.valueOf(BlockType.EMPTY), 0);
//[2026/04/03 17:29:36 SEVERE]         [World|default] Failed to run task!
//java.lang.IllegalArgumentException: Unknown key! BlockType{id=Empty, ...}
//	at com.hypixel.hytale.server.core.universe.world.accessor.BlockAccessor.setBlock(BlockAccessor.java:48)
//	at com.hypixel.hytale.server.core.universe.world.accessor.IChunkAccessorSync.setBlock(IChunkAccessorSync.java:136)
//	at com.arkatale.defenseplugin.systems.DefendBlockSystem.lambda$stuff$0(DefendBlockSystem.java:106)
-> with
world.setBlock(posI.x, posI.y, posI.z, BlockType.EMPTY_KEY, SetBlockSettings.NO_SEND_PARTICLES);



[2026/04/02 17:23:00 SEVERE]                     [NPC|P] Error: java.lang.IllegalArgumentException: Asset 'Component_Instruction_Intelligent_Idle_Motion_Follow_Path'(368) is different type. Is 'com.hypixel.hytale.server.npc.instructions.Instruction' but should be 'com.hypixel.hytale.server.npc.instructions.ActionList' for NPC null
-> Fix: 
    Instruction (with indent) instead of action without indent

Fixed Template_NexusAttacker_Base.json (NexusAttacker_Goblin) starts with start.Default
-> need to set StartState

Doesn't change to FollowPath State
-> Remove BodyMotion Nothing & Instruction (with indent) instead of action without indent & Remove Any
now correctly walks to the /path new ... I set earlier (but doesn't change the Debug Displaystate above head)