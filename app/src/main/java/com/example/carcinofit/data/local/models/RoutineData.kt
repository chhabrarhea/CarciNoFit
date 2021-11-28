package com.example.carcinofit.data.local.models

object RoutineData  {
    private fun getObliqueExercises():ArrayList<Exercise>{
        val list=ArrayList<Exercise>()
        list.add(Exercise( "Side Plank", "Lie on your left side with your right leg stacked on top of your left leg, and your left forearm on the ground with your elbow underneath your shoulder.Your body should form a straight line from head to feet that makes about a 45-degree angle with the ground. Raise your right arm up toward the ceiling without letting your hips dip.", "o_sp.gif", 30,0,true))
        list.add(Exercise("Standing Trunk Rotation", "Stand with your feet hip-width apart and hold a medicine ball between your hands. Keeping your core tight, your arms bent at right angles at the elbows, rotate your torso slowly to your right, keeping head and chest rotating along with it. Hold the twist briefly before rotating to your left.", "o_sr.gif",45,0,true))
        list.add(Exercise("Standing Wood Chop", "Stand in a split stance with your right foot forward and your feet slightly apart. Hold a medicine ball in your hands and lift it to your right so it is slightly higher than shoulder height and extended diagonally away from the body. Then, in a fluid motion, bring the medicine ball toward your left hip.", "o_wc.gif",45,0,true))
        list.add(Exercise("Russian Twist", "Sit on a mat with your legs slightly bent in front of you. Lean back so your torso and thighs form a V shape and lift your feet slightly off the ground. Hold your arms straight out in front of you, then twist your torso to one side in a controlled motion, tapping the floor before rotating to the other side and repeating.", "o_rt.gif",40,0,true))
        list.add(Exercise("Mountain Climber","Begin in a plank position forming a straight line from head to toes parallel with the ground. Engage your core as you bring your right knee into your chest, keeping your right toes off the ground. Return to the plank position and repeat the move with your left knee.","o_mc.gif",50,0,true))
        list.add(Exercise("Side Bend","Start standing with your feet hip-width apart. Bend your upper body sideways toward the right. Pause and then return to standing while squeezing your left side obliques.","o_sb.gif",30,0,true))
        list.add(Exercise("Dead Bug","Lie on your back with your arms extended up toward the ceiling. Bend your knees so shins and thighs form a 90-degree angle. Engage your core and lower your right arm straight back behind you at the same time you extend your left leg out long in front of you, lowering it so it hovers just above the ground.","o_db.gif",40,0,true))
        list.add(Exercise("Extended Side Angle Pose","Place your hands on your hips with your feet in a wide stance. Turn your right foot so it’s pointing to your right. Bend your right knee and lower body down into a lunge position. Bend your right elbow and rest it on your right thigh, twisting your torso so it faces your left side, keeping the head aligned with your spine and also facing left. Lift your left arm overhead with your palm facing the floor and stretch it alongside your left ear.","o_sa.gif",35,0,true))
        return list
    }
    private fun getFatBurningExercises():ArrayList<Exercise>{
        val list=ArrayList<Exercise>()
        list.add(Exercise("Jumping Jacks","Start in a standing position with your arms resting at your sides. Slightly bend your knees and jump your legs out so they’re a little more than shoulder-width apart. At the same time, thrust your arms out and over your head.","fb_jj.gif",40,0,true))
        list.add(Exercise("Burpees","Stand with your feet shoulder-width apart. In one fluid motion, lower your body into a squat, place your hands on the ground in front of your feet, and jump your feet back so you land in a plank position. Then jump to return your feet to near your hands and complete a powerful jump straight up into the air.","fb_burpees.gif",0,10,true))
        list.add(Exercise("Squat Jumps","With your feet shoulder-width apart, lower your body into a squat position. Keep your core tight and launch into an explosive jump. Land lightly on your feet and immediately lower into a squat again.", "fb_sqj.gif",0,12,true))
        list.add(Exercise("Skater Jumps","With your right foot planted, cross your left leg behind you and out to your right side. Simultaneously, swing your right arm out to about shoulder height and your left arm across your body reaching toward your right hip. Then jump to your left and repeat the movement on your left side.","fb_sj.gif",40,0,true))
        list.add(Exercise("Plank Jumps","Begin in a plank position with your wrists in line with your shoulders and your body extended in a straight line behind you. Your feet should be planted together on the floor. Jump your legs out wide, and then jump them back together at a quick pace.","fb_pj.gif",0,12,true))
        list.add(Exercise("High Knees","From a standing position, lift your left knee into your chest. Swiftly switch your legs. At a nonstop pace, keep alternating your knees and move your arms in a running motion.","fb_hk.gif",40,0,true))
        return list
    }
    private fun getLegExercises():ArrayList<Exercise>{
        val list=ArrayList<Exercise>()
        list.add(Exercise("Bodyweight Squats","Stand with your feet shoulder-width apart. Lower your hips and butt downward into a squat position. Keep your weight shifting back in your heels and your chest lifted up. Pause at the bottom and then drive up through the heels to stand.","l_ss.gif",0,12,true))
        list.add(Exercise("Dumbell Dead Lift","Hinge forward at the hips to lower your hands down the front of your legs, keeping the weights close to your body and tilting your back and upper body forward. Keep your back flat and maintain a slight bend in your knees.","l_dd.gif",40,0,true))
        list.add(Exercise("Alternating Lateral Lunge","Start standing with feet together. Step your right leg wide out to your right side, bending the right knee as your foot touches the ground. Squeeze your inner thighs together to push off of your right foot and return to standing.","l_ll.gif",35,0,true))
        list.add(Exercise("Calf Raises","Stand with your feet shoulder-width apart and your toes pointing straight ahead. Use your calf muscles to lift your heels off the floor. Pause at the top and then lower to the ground. ","l_cr.gif",30,0,true))
        list.add(Exercise("Reverse Lunges","Start standing with feet together. Step your right foot directly behind you. Lower your hips and drop your right knee and your right heel is lifted off the ground. Squeeze your glutes, quads, and calves as you press your left heel into the ground and bring your right leg forward to return to standing.","l_rl.gif",30,0,true))
        list.add(Exercise("Sumo Squats","Start by standing with your feet wider than hip-width apart and your toes pointed out at an angle of about 45 degrees. Bend your knees and lower your hips into a wide squat until your thighs are parallel to the ground, keeping chest lifted. Pause at the bottom and then push through your heels to return to standing. ","l_ss.gif",35,0,true))
        list.add(Exercise("Burpees","Stand with your feet shoulder-width apart. In one fluid motion, lower your body into a squat, place your hands on the ground in front of your feet, and jump your feet back so you land in a plank position.\nThen jump to return your feet to near your hands and complete a powerful jump straight up into the air.","l_burpee.gif",40,0,true))
        return list
    }

    private val obliques=Routine(1,"Stronger Obliques","obliques_header.jpg","The obliques work together to control movement in the spine, rib cage, and pelvis.",70,21, getObliqueExercises(),"#fa8d7c")
    private val fatBurning=Routine(2,"Fat Burning","fat_burning.png","Comfortable but challenging intensity for optimal fat burning.",80,30, getFatBurningExercises(),"#a4d3c3")
    private val legRoutine=Routine(3,"Legs","legs.jpg","Target your thighs and calves for well defined legs.",80,10, getLegExercises(),"#4cc3bb")
    private val running=Routine(0,"Running","running.png","",0,0,ArrayList(),"#4c7c94")
    private val yoga=Routine(4,"Relaxing Yoga","yoga_header.jpg","Ease angst today, build resilience for the long term.",40,15,getYogaExercises(),"#95cbd7")
    private val faceYoga=Routine(5,"Face Yoga","face_yoga_header.jpg","Increase the blood and oxygen supply for glowing and rejuvenated skin.",10,10,getFaceYogaExercises(),"#e38c73")

    private fun getFaceYogaExercises(): List<Exercise> {
       val list=ArrayList<Exercise>()
        list.add(Exercise("Cheek Lifter","Open your mouth and form an O. Position your upper lip over your teeth and smile to lift cheek muscles up. Place your index fingers lightly on top of your cheek muscles, directly under your eyes. Release cheek muscles to lower them.","fy_cl.gif",60,0,true))
        list.add(Exercise("Happy Cheeks Sculpting","Smile without showing your teeth and roll your lips out as if trying to show as much lip as possible. Try to smile with the corners of your mouth so you feel a slight burn in the corners of your mouth. Press your index fingers into the corners of your mouth and slide the fingers up to your cheekbones as you press into the muscle. Lift the muscle up to the cheekbones, toward the corners of your eyes.","fy_hcs.gif",60,0,true))
        list.add(Exercise("Eyebrow Lifter","Press three fingertips under each of your eyebrows to force your eyes open. Smile as you try to lower your eyebrows against your fingers. Hold. Then close your eyes and roll your eyeballs toward the top of your head.","fy_el.gif",60,0,true))
        list.add(Exercise("Temple Developer","Press your fingertips into your temples as you close your jaw, clenching your teeth together and tilting your chin up. Clench your teeth and concentrate on the temple region, thinking about trying to move your ears backward. Hold for 10 seconds, then clench your back teeth down and hold for 10 seconds. ","fy_td.gif",60,0,true))
        list.add(Exercise("Jaw and Neck Firmer","Open your mouth and make an “aah” sound. Fold your lower lip and the corners of your lips into your mouth and hold tightly as you extend your lower jaw forward. Using your lower jaw, scoop up slowly as you close your mouth, pulling your chin up about 1 inch each time you scoop and tilting your head backward. ","fy_jnf.gif",60,0,true))
        return list
    }


    private fun getYogaExercises(): List<Exercise> {
        val list=ArrayList<Exercise>()
        list.add(Exercise("Baddha Konasana","Lie on your back, bend your knees, and place feet flat on the ground. Then drop your knees out wide with the soles of your feet touching so your legs form a diamond. Place one hand on your heart and the other on your belly.","y_rbap.jpg",120,0,false))
        list.add(Exercise("Paschimottanasana","Sit on the floor with your legs stretched out straight in front of you. Keeping your torso long, fold forward and reach for your toes. Reach as far as you can without shaking.","y_sfb.jpg",60,0,false))
        list.add(Exercise("Anuvittasana","Stand with your feet slightly apart. Keep arms at your sides, with face palms facing forward. Raise arms up above your head toward the sky and then slowly reach your arms back, arching your back and opening up your chest.","y_ssa.gif",0,10,true))
        list.add(Exercise("Supta Kapotasana","Lie on your back and bring knees up toward your chest. Fold your right leg, so that your heel rests on the left knee and your right knee sticks out to the side. Reach behind your left leg and pull toward you.","y_hpld.jpg",120,0,false))
        list.add(Exercise("Viparita Karani","Sit with your right side against a wall. Lie down, then pivot your body toward the wall and swing your legs up the wall so they’re resting against it. ","y_lutw.jpg",120,0,false))
        return list
    }

    val routines=ArrayList<Routine>(listOf(obliques, fatBurning, legRoutine, running,
        faceYoga, yoga))
}

