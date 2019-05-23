import React, { Component } from 'react'
import { changeDateFormat1 } from './../../../../functions'
import './PostCard.scss'
import TempPic from './../../../../assets/default-profile-pic.png'
import { Dropdown } from 'react-materialize'
import MoreIcon from './../../../../assets/more.png'
import ClapIcon from './../../../../assets/clap.png'
import WowIcon from './../../../../assets/wow.png'

class PostCard extends Component {

    render() {
    return (
        <div className="post-card">
            <div className="post-owner">
                <img src={ TempPic  }></img>
                <div className="post-card-owner-info">
                    <span className="post-card-owner">{this.props.creatorLogin}</span>
                    <span className="post-card-date">{changeDateFormat1(this.props.createdDate)}</span>
                </div>
            </div>
            <div className="post-content">
                <p className="post-card-text">{this.props.postText}</p>
            </div>
            <div className="post-bottom-navigation">
                <div className="post-reactions">
                    <div className="reaction">
                        <img src={ClapIcon}></img>
                        <span className="reaction-counter">{this.props.counter_CLAPPING}</span>
                    </div>
                    <div className="reaction">
                        <img src={WowIcon}></img>
                        <span className="reaction-counter">{this.props.counter_WOW}</span>
                    </div>
                    <div className="reaction">
                        <img src={ClapIcon}></img>
                        <span className="reaction-counter">{this.props.NS}</span>
                    </div>
                    <div className="reaction">
                        <img src={ClapIcon}></img>
                        <span className="reaction-counter">{this.props.counter_TTD}</span>
                    </div>
                </div>
                <button className="show-comments-btn">Show comments</button>

            </div>

            {this.props.currentUserLogin === this.props.creatorLogin ? 
                <Dropdown trigger={ <a href="#!" className='post-card-more-btn dropdown-trigger' data-target={this.props.id}><img src={MoreIcon}></img></a>}>
                    <a className="dropdown-item delete-post" href="#!" onClick={() => this.props.handlePostCardDeleted(this.props.id)}>Delete</a>
                </Dropdown>
                : null}
        </div>
    )}
}

export default PostCard;