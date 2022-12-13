package com.example.composetutorial

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composetutorial.ui.theme.ComposeTutorialTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTutorialTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                )
                {
                    Conversation(SampleData.conversationSample)
                }
            }
        }
    }
}

data class Message(val author: String, val body: String)

@Composable
fun Conversation(messages: List<Message>) {
    LazyColumn {
        messages.map { item { msgCard(it) } }
    }
}

@Preview
@Composable
fun PreviewConversation(){
    ComposeTutorialTheme() {
        Conversation(SampleData.conversationSample)

    }
}

@Composable
fun msgCard(msg: Message)
{
    Row(modifier = Modifier.padding(all = 8.dp))
    {
        Image(
            painter = painterResource(id = R.drawable.profile_picture),
            contentDescription = "Contact Profile Picture",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
        )
        
        Spacer(modifier = Modifier.width(8.dp))

        var isExapanded by remember {
            mutableStateOf(false)
        }
        val surfaceColor by animateColorAsState(
            if (isExapanded) MaterialTheme.colors.primary else MaterialTheme.colors.surface,
        )
        
        Column (modifier = Modifier.clickable { isExapanded =! isExapanded }){
            Text(text = msg.author,
                 color = MaterialTheme.colors.secondaryVariant,
                 style = MaterialTheme.typography.subtitle2)

            Spacer(modifier = Modifier.height(4.dp))


            Surface(shape = MaterialTheme.shapes.medium,
                elevation = 1.dp,
                color = surfaceColor,
                // animateContentSize will change the Surface size gradually
                modifier = Modifier.animateContentSize().padding(1.dp))
            {
                Text(text = msg.body,
                    modifier = Modifier.padding(all = 4.dp),
                    maxLines = if(isExapanded)Int.MAX_VALUE else 1,
                    style = MaterialTheme.typography.body2)
            }
        }
    }
}

@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)

@Preview
@Composable
fun previewMsgCard()
{
    ComposeTutorialTheme{
        msgCard(
            msg = Message("Colleague", "Hey, take a look at Jetpack Compose, it's great!")
        )
    }
}