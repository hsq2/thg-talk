import {NotFoundPage, ImageSad} from "./App.styles"

export const NotFound : React.FC = () => {
    return (
        <NotFoundPage>
        <ImageSad src="/Sadness.jpg" data-testid='not-found-image'/>
        <div className="oops">Oops..</div>
        <p>We can't seem to find the page you're looking for :(</p>
        </NotFoundPage>
    )
}