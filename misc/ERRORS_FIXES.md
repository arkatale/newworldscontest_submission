[2026/04/02 17:23:00 SEVERE]                     [NPC|P] Error: java.lang.IllegalArgumentException: Asset 'Component_Instruction_Intelligent_Idle_Motion_Follow_Path'(368) is different type. Is 'com.hypixel.hytale.server.npc.instructions.Instruction' but should be 'com.hypixel.hytale.server.npc.instructions.ActionList' for NPC null
-> Fix: 
    Instruction (with indent) instead of action without indent

Fixed Template_NexusAttacker_Base.json (NexusAttacker_Goblin) starts with start.Default
-> need to set StartState

Doesn't change to FollowPath State
-> Remove BodyMotion Nothing?