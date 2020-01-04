import React from 'react'
import TempPic from './../../../../assets/default-profile-pic.png'

function MemberCard(props) {

        return (
            <li className="collection-item">
                <img src={`data:image/png;base64,${props.profilePic}`} alt="User avatar" title="User avatar" width="128" height="128" />
                <span className="member-login">{props.userLogin}</span>
                {props.isAdmin && (props.currentUser !== props.userID) ? <button onClick={() => props.banUser(props.habitID, props.userID)}>👊 kick</button>
                : null}
            </li>
        )

}

export default MemberCard;